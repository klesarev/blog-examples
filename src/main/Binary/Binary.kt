package Binary

import DSL.*
import kotlinx.serialization.json.JsonNull.content
import org.apache.batik.svggen.font.table.Table.head
import org.apache.commons.codec.binary.Hex.encodeHexString
import org.bouncycastle.util.encoders.Hex
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO


fun main() {


    val s =   Hex.toHexString("kotlin".toByteArray())


    val res = html {
        head {
            title {+"XML encoding with Kotlin"}
            meta { +"dfgdfg"
                attributes["description"] = "some desc!!!!"
            }

        }
        body {
            p { attributes["class"] = "text_edd"
                attributes["id"] = "text_id"
                +"sdfsdfsd"

                    for (arg in attributes)
                        +"${arg}"

            }
        }
        foter {
            +"dfd"
        }
    }
    println(res)

    // 1111 1111 1111 1111 1111 1111 1111 1111 (FFFFFFFF)
    // 1111 1111 0000 0000 0000 0000 0000 0000 (FF000000)
    // 1111 1111 0000 0000 0000 0000           (FF0000)


}

fun BufferedImage.grayscale(): BufferedImage {
    val result = BufferedImage(this.width,this.height,this.type)

    for (x in 0..this.width-1) {
        for (y in 0..this.height-1) {
            val c = Color(this.getRGB(x, y))
            val red = (c.red * 0.299).toInt()
            val green = (c.green * 0.587).toInt()
            val blue = (c.blue * 0.114).toInt()
            val newColor = Color(
                red + green + blue,
                red + green + blue, red + green + blue
            )

            this.setRGB(x, y, newColor.rgb)
        }
    }

    return this
}
fun BufferedImage.setHSL(hue: Float, saturiation: Float, brightness: Float): BufferedImage {
    for (x in 0 until this.width) {
        for (y in 0 until this.height) {
            val alpha = this.getRGB(x,y).toLong() and 0xff000000 shr 24
            val red = this.getRGB(x,y).toLong() and 0x00ff0000 shr 16
            val green = this.getRGB(x,y).toLong() and 0x0000ff00 shr 8
            val blue = this.getRGB(x,y).toLong() and 0x000000ff

            val hsb = Color.RGBtoHSB(red.toInt(), green.toInt(), blue.toInt(), null)
            val h = hsb[0]
            val s = hsb[1]
            val b = hsb[2]

            val rgbColor = Color.HSBtoRGB(h * hue, s*saturiation, b*brightness)

            this.setRGB(x,y,rgbColor)
            println(rgbColor)
        }
    }

    return this
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

