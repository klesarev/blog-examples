package Lambda

import java.awt.Color
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.random.Random


fun main(vararg args: String ) {
    val arr = listOf("Ford", "Mazda", "Kia", 765, true)
        println(md5("Ford"))
    println("1083da2df15d6ebfe62186418a76863")
    val pc = listOf("Dell", "Mac", "Lenovo")
    val fib = listOf(1, 1, 2, 3, 5, 8, 13)
    println("PS: ${pc.getFirst}, Fib: ${fib.getFirst}")


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



class Gangster(val id: Int, val name: String) {
    fun getInfo():String {
        return "ID: $id, Name: $name"
    }
}
class Cage(var gangster: Gangster, val size: Int)
