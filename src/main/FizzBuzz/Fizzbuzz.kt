package FizzBuzz

import java.io.Serializable
import java.util.stream.IntStream
import kotlin.system.measureTimeMillis


fun main() {

    // решение через цикл
    fizzBuzz()

    // решение через when
    fizzBuzzWhen()

    // решение через потоки - Java IntStream
    fizzBuzzJava()

}

fun fizzBuzz() {
    for (n in 1..100){
        if(n % 15 == 0) {
            println("Fizz Buzz = $n")
            continue
        } else if (n % 5 == 0) {
            println("Buzz = $n")
            continue
        } else if (n % 3 == 0) {
            println("Fizz = $n")
            continue
        }
        println("$n")
    }
}

fun fizzBuzzWhen() {
    for (it in 1..100) {
        when {
            it % 15 == 0 -> {
                println("Fizz Buzz = $it")
                continue
            }
            it % 5 == 0 -> {
                println("Buzz = $it")
                continue
            }
            it % 3 == 0 -> {
                println("Fizz = $it")
                continue
            }
            else -> println("$it")
        }
    }
}

fun fizzBuzzJava() = IntStream.rangeClosed(1, 100)
    .mapToObj { n: Int -> if (n % 3 == 0)
        if (n % 15 == 0) "Fizz Buzz = $n"
        else "Fizz = $n"
    else if (n % 5 == 0) "Buzz = $n"
    else n
    }
    .forEach { x: Serializable? -> println(x) }