package id3

import org.bouncycastle.util.encoders.BufferedDecoder
import org.bouncycastle.util.encoders.Hex
import org.bouncycastle.util.encoders.HexEncoder
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.file.Files
import java.nio.file.Path


@ExperimentalUnsignedTypes
fun main() {
    val f = Files.readAllBytes(Path.of("D:/Numb.mp3"))

    val bytes: ByteArray = ByteArray(10)
    val data = f.toUByteArray()

    for (b in 0..10) {

        println(
           data[b].toByte()
        )
    }

}