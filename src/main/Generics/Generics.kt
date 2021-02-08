package Generics

import HttpRequest.RequestHelper
import kotlinx.coroutines.*
import kotlin.collections.ArrayList
import kotlin.random.Random


fun main() {


    (1..100)
        .toList()
        .map { println(fizzBuzz(it)) }


}

fun fizzBuzz(n: Int) =
    when {
        n % 15 == 0 -> "FizzBuzz"
        n % 5 == 0 -> "Buzz"
        n % 3 ==0 -> "Fizz"
        else -> n
    }

fun <T,V>List<T>.myFilter(list: List<T>, block: (T)-> Boolean) {

}

fun <T, V>getsum(x: T, y: T, block: (T,T) -> V ): V {
    return block(x,y)
}


fun array2D(row: Int, cell: Int): Array<ArrayList<Any?>> {

    val numbers:Array<ArrayList<Any?>> = Array(row) { ArrayList(cell) }

    for (i in 0 until row) {
        for (j in 0 until cell) {
            val randomize = Random.nextInt(8)
            when(randomize<4) {
                true -> numbers[i].add(Random.nextInt(100))
                else -> numbers[i].add("")
            }
        }
    }

    return numbers
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
