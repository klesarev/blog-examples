package Generics

import HttpRequest.RequestHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope


fun main() {

    val res = callback(listOf<Int>(1,2,3)) { elem ->
        elem.map { it * 2 }
    }
    println(res)

}


fun<T,V> callback(data: T, callback: (T) -> V?): V? {
    return callback(data)
}

suspend fun <T, V> Iterable<T>.pMap(func: suspend (T) -> V): Iterable<V> =
    coroutineScope {
        map { element ->
            async(Dispatchers.IO) { func(element) }
        }.awaitAll()
}
