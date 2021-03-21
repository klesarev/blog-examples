package Binary

import DSL.*
import kotlinx.serialization.json.JsonNull.content
import org.apache.batik.svggen.font.table.Table.head
import org.apache.commons.codec.binary.Hex.encodeHexString
import org.bouncycastle.util.encoders.Hex
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import java.io.FileOutputStream
import java.lang.StringBuilder
import javax.imageio.ImageIO


fun main() {

    println(fibonacci.take(4).toList())
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

