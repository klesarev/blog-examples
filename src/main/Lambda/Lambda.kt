package Lambda

import HttpRequest.RequestHelper
import HttpRequestAsync.getHttpAsync
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.awt.Color
import java.math.BigInteger
import java.security.MessageDigest
import java.util.concurrent.CompletableFuture
import java.util.function.Supplier
import java.util.stream.Collectors
import kotlin.random.Random
import kotlin.system.measureTimeMillis


/*
* Рассматриваем parallestream, pmap и flow.
* Flow выаполняется постепенно и сразу
* parallelStream быстрее в 2 раза чем pmap
* */



suspend fun main() {

//    val time = measureTimeMillis {
//        val res = (10 downTo 0).toList()
//            .parallelStream()
//            .map { getHttpAsync("https://jsonplaceholder.typicode.com/comments/$it") }
//            .collect(Collectors.toList())
//
//        println(res)
//
//    }
//    println("ParallelStream Time: $time")
//
//    val time2 = measureTimeMillis {
//        runBlocking {
//            val res2 = (1..10).toList()
//                .pmap { RequestHelper().getData("https://jsonplaceholder.typicode.com/comments/$it") }
//
//            //println(res2)
//        }
//    }
//    println("PMAP Time: $time2")
//
//    val time3 = measureTimeMillis {
//        runBlocking {
//            httpFlow()
//                .conflate()
//                .collect{ value -> println(value) }
//        }
//    }
//    println("flow $time3")
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
//    println("DIO $time4")


//    val time5 = measureTimeMillis {
//        runBlocking {
//           println(asyncHttp(10 downTo 0))
//        }
//    }
//    println("AsyncHTTP: $time5")

    GlobalScope.launch { // launch a new coroutine and keep a reference to its Job
        delay(1000L)
        println(loadComment(1))
    }

}

suspend fun loadComment(id: Int): String? =
    withContext(Dispatchers.Default) {
        getHttpAsync("https://jsonplaceholder.typicode.com/comments/$id")
    }


inline fun load(link: String, callback: (String?) -> Unit) {
    callback(RequestHelper().getData(link))
}

suspend fun <T>asyncHttp(range: Iterable<T>) = coroutineScope {
    range.map { elem ->
        async(Dispatchers.IO) {
            getHttpAsync("https://jsonplaceholder.typicode.com/comments/$elem")
        }
    }.awaitAll()
}

fun httpFlow() = flow {
    for (i in 1..10) {
        emit(RequestHelper().getData("https://jsonplaceholder.typicode.com/comments/$i"))
    }
}.flowOn(Dispatchers.Default)

suspend fun <A, B> Iterable<A>.pmap(f: suspend (A) -> B): List<B> = coroutineScope {
    map { async { f(it) } }.awaitAll()
}

var color = { red: Int, green: Int, blue: Int ->
    val R = if (red <= 0 || red > 255) 0 else Random.nextInt(0, red)
    val G = if (green <=0 || green > 255) 0 else Random.nextInt(0, green)
    val B = if (blue <=0 || blue > 255) 0 else Random.nextInt(0, blue)
    Color(R,G,B)
}

var md5 = { input: String ->
    val md = MessageDigest.getInstance("MD5")

    BigInteger(1, md.digest(input.toByteArray()))
        .toString(16)
        .padStart(32, '0')
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
