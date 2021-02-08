package Lambda

import HttpRequest.RequestHelper
import HttpRequestAsync.getHttpAsync
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.awt.Color
import java.math.BigInteger
import java.security.MessageDigest
import java.util.concurrent.Callable
import java.util.concurrent.CompletableFuture
import java.util.function.Supplier
import java.util.stream.Collectors
import kotlin.random.Random
import kotlin.system.measureTimeMillis
import java.util.concurrent.ForkJoinPool
import java.util.concurrent.ExecutionException

import java.util.stream.IntStream


/*
* Рассматриваем parallestream, pmap и flow.
* Flow выаполняется постепенно и сразу
* parallelStream быстрее в 2 раза чем pmap
* */



const val URL = "https://jsonplaceholder.typicode.com/comments"

fun main() {

    val asyncTime = measureTimeMillis {
        val result = (1..10).toList()
            .map {
                getHttpAsync("$URL/$it")
            }
        result.forEach { println(it) }
    }
    println("Асинхронный запрос время $asyncTime мс")

//    val parallelHttpTime = measureTimeMillis {
//        runBlocking {
//            println(parallelHttp(1..10))
//        }
//    }
//    println("ParallelHttp: $parallelHttpTime ms")
//
//    val time3 = measureTimeMillis {
//        runBlocking {
//            httpFlow()
//                .conflate()
//                .collect{ value -> println(value) }
//        }
//    }
//
//    println("ParallelHttp: $time3 ms")
//
//    val time4 = measureTimeMillis {
//        runBlocking {
//            val res = withContext(Dispatchers.IO) {
//                val res = (1..10).toList()
//                    .map { RequestHelper().getData("https://jsonplaceholder.typicode.com/comments/$it") }
//            }
//
//            println(res)
//        }
//    }
//
//    val time5 = measureTimeMillis {
//        runBlocking {
//           println(asyncHttp(20 downTo 10))
//        }
//    }
//
//    val time6 = measureTimeMillis {
//        runBlocking {
//            val res2 = (20 downTo 10)
//                .parallelMap {
//                    RequestHelper().getData("https://jsonplaceholder.typicode.com/comments/$it")
//                }
//        }
//    }

    val fibonacci = sequence<Int> {
        var cur = 1
        var next = 1
        while (true) {
            yield(cur) // суспенд, ждет когда попросят выолнения
            val tmp = cur + next
            cur = next
            next = tmp
        }
    }

}

//suspend fun loadComment(id: Int): String? =
//    withContext(Dispatchers.Default) {
//        getHttpAsync("https://jsonplaceholder.typicode.com/comments/$id")
//    }


inline fun loadCallback(link: String, callback: (String?) -> Unit) {
    callback(RequestHelper().getData(link))
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


// медленная из-за отсутствия dispatchers.io
suspend fun <A, B> Iterable<A>.pmap(f: suspend (A) -> B): List<B> = coroutineScope {
    map { async { f(it) } }.awaitAll()
}


var miletokm = { miles: Int ->
    miles * 1.60934
}


// generics
fun <T>Iterable<T>.stringify(): String {
    return this.joinToString(" ")
}
val <T>List<T>.getFirst: T
    get() = this[0]
