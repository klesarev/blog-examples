package DrawImage

import java.awt.Color
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.TYPE_INT_RGB
import kotlin.random.Random
import javax.imageio.ImageIO

import java.io.File
import java.lang.Exception


fun main() {
    drawImage(600,600,"D:/test.bmp",5)
}

fun drawImage(width: Int, height: Int, file:String, redRng: Int = 256, greenRng: Int = 256, blueRng: Int = 256) {

    val img = BufferedImage(width,height,TYPE_INT_RGB)

    for(posX in 0 until width){
        for (posY in 0 until height) {
            val r = if (redRng <=0) 0 else Random.nextInt(0, redRng)
            val g = if (greenRng <=0) 0 else Random.nextInt(0, greenRng)
            val b = if (blueRng <=0) 0 else Random.nextInt(0, blueRng)
            val color = Color(r,g,b).rgb
            img.setRGB(posX, posY, color)
        }
    }

    try {
        Thread(Runnable {
            ImageIO.write(img, File(file).extension, File(file))
        }).start()
    } catch (ex: Exception) {
        ex.printStackTrace()
    }

}