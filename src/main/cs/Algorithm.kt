package cs

import DSL.*
import java.math.BigInteger


fun main() {
    println(factorial.take(20).toList())
}

val fibonacci = sequence {
    var start = Pair(0,1)

    while (true) {
        val x = start.first
        val y = start.second
        val next = Pair(x + y, (x + y) + y)
        yield(start)
        start = next
    }
}

val factorial = sequence {

    var fn = 1
    var res: BigInteger = BigInteger.ONE

    while (true) {
        yield(res)
        fn++
        res = res.multiply(fn.toBigInteger())
    }
}

fun minArr(array: ArrayList<Int>): Int {
    var result = array[0]

    for (n in 1 until array.size) {
        if (result > array[n]) {
            result = array[n]
        }
    }
    return result
}

fun <T>Array<T>.reversedArray(): Array<T> {
    var index = this.size-1
    for (n in this.indices) {
        this[n] = this[index]
        index--
    }
    return this
}

fun isPrime(n: Int): Boolean {
    var s = true
    loop@ for(number in 2 until n) {
        if (n % number == 0) {
            s = false
            break@loop
        }
    }
    return s
}

