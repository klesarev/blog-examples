package Generics

import FileDataHelper.FileDataHelper
import HttpRequest.RequestHelper
import kotlinx.coroutines.*


fun main() {


}

val <T:Number> List<T>.summ: Int
    get() {
        var tmp = 0
        this.map { tmp += it.toInt() }
        return tmp
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
