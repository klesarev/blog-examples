package Password

import java.awt.Robot
import java.awt.event.KeyEvent
import java.lang.StringBuilder
import kotlin.random.Random


fun main() {

    println(passGen(10))

}


fun passGen(size: Int, useDigits: Boolean = true, useSigns: Boolean = false, useUpper: Boolean = false): String {
    val letters = "abcdefghijklmnopqrstuvwxyz"
    val digits = "1234567890"
    val signs = "%*)?@#$~_"

    val randLetters =  letters.randchars(size,useUpper)
    val randDigits = digits.randchars(size,false)
    val randSigns = signs.randchars(size,false)

    val passString = when {
        useDigits && !useSigns -> randLetters + randDigits
        useDigits && useSigns -> randLetters + randDigits + randSigns
        else -> randLetters
    }
    return passString.toList().shuffled().take(size).joinToString("")
}

fun String.randchars(limit: Int, useUpper: Boolean): String {
    val res by lazy {
        StringBuilder()
    }

    (1..limit).forEach {
        val randomFactor = Random.nextInt(2)
        val randomChar = this[Random.nextInt(0,this.length)]

        when(randomFactor > 0 && useUpper) {
            true -> res.append(randomChar.toUpperCase())
            else -> res.append(randomChar)
        }

    }
    return res.toString()
}
