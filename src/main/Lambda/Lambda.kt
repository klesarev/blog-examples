package Lambda

import HttpRequest.RequestHelper
import HttpRequestAsync.getHttpAsync
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.future.await
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.concurrent.CompletableFuture
import kotlin.system.measureTimeMillis
import java.net.http.HttpResponse.BodyHandlers
import java.time.Duration
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.stream.Collectors
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


const val URL = "https://jsonplaceholder.typicode.com/comments"

suspend fun main() {
val httpClient:HttpClient = HttpClient.newBuilder().build()
val result = measureTimeMillis {
    runBlocking {
        val res =(1..10).toList()
            .map { async { getMyData(httpClient, "$URL/$it") } }
            .awaitAll()
        println(res)
    }
}
println("Time for requests: $result")

    loadCallback("$URL/2") { res ->
        println(
            getMyData(httpClient,res).let {
                it?.substring(20)
            }
        )
    }



}



suspend fun getMyData(httpClient: HttpClient, url: String): String? =
    suspendCoroutine {
        val httpRequest = HttpRequest.newBuilder().uri(URI.create(url)).build()
        httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
            .thenApply { obj -> it.resume(obj.body()) }
}

suspend fun getData(httpClient: HttpClient, url: String): String? {
    val httpRequest = HttpRequest.newBuilder().uri(URI.create(url)).build()
    return httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
        .await().body()
}

inline fun <T, V>loadCallback(data: T, callback: (T) -> V) {
    callback(data)
}

suspend fun <T>parallelHttp(range: Iterable<T>) = coroutineScope {
    range.map { elem ->
        async(Dispatchers.IO) {
            getHttpAsync("https://jsonplaceholder.typicode.com/comments/$elem")
        }
    }.awaitAll()
}

// мой вариант параллельной мапы
suspend fun <T, V> Iterable<T>.parallelMap(func: suspend (T) -> V): Iterable<V> = coroutineScope {
    map { element ->
        async(Dispatchers.IO) { func(element) }
    }.awaitAll()
}


fun httpFlow(): Flow<String> = flow {
    for (i in 1..10) {
        emit(RequestHelper().getData("https://jsonplaceholder.typicode.com/comments/$i"))
    }
}.flowOn(Dispatchers.IO)


var miletokm = { miles: Int ->
    miles * 1.60934
}


// generics
fun <T>Iterable<T>.stringify(): String {
    return this.joinToString(" ")
}
val <T>List<T>.getFirst: T
    get() = this[0]
