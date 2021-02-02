package DrawImage

import java.awt.Color
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.TYPE_INT_RGB
import kotlin.random.Random
import javax.imageio.ImageIO

import java.io.File
import java.lang.Exception

/*
* https://stackoverflow.com/questions/16497390/illegalargumentexception-color-parameter-outside-of-expected-range-red-green-b
* But color.getRed() (Blue, Green) can return a value up to 255. So you can get the following
*/

fun main() {

    // prints pixel heart
    val map = arrayOf(
        listOf(0,2,2,0,0,0,0,2,2,0),
        listOf(2,1,1,2,0,0,2,1,1,2),
        listOf(2,1,1,1,2,2,1,1,1,2),
        listOf(2,1,1,1,1,1,1,1,1,2),
        listOf(2,1,1,1,1,1,1,1,1,2),
        listOf(2,1,1,1,1,1,1,1,1,2),
        listOf(0,2,1,1,1,1,1,1,2,0),
        listOf(0,0,2,1,1,1,1,2,0,0),
        listOf(0,0,0,2,1,1,2,0,0,0),
        listOf(0,0,0,0,2,2,0,0,0,0)
    )

    val heartImage = BufferedImage(100,100,TYPE_INT_RGB)
    drawIcon(map,heartImage)
    writeImage(heartImage,"D:/heart.bmp")

}

fun drawIconFromText() {
    // load map of pixels from txt file
}

fun drawIcon(pixels: Array<List<Int>>, image: BufferedImage) {
    pixels.forEachIndexed { posY, rowElement ->
        rowElement.forEachIndexed { posX, colElement ->
            when(colElement) {
                1 -> drawTile(posX*10,posY*10,10,255,2,0,image)
                2 -> drawTile(posX*10,posY*10,10,0,0,0,image)
                else -> drawTile(posX*10,posY*10,10,255,255,255,image)
            }
        }
    }
}

fun writeImage(img: BufferedImage, file: String) {
    try {
        Thread(Runnable {
            ImageIO.write(img, File(file).extension, File(file))
        }).start()
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
}

fun drawTile(startX: Int, startY: Int, size: Int, red: Int, green: Int, blue: Int, image: BufferedImage) {
    for (posX in startX until startX+size) {
        for (posY in startY until startY+size) {
            drawPixel(posX,posY,red,green,blue,image)
        }
    }
}

fun drawPixel(x:Int, y:Int, red:Int, green:Int, blue: Int, image: BufferedImage) {
    image.setRGB(x, y, Color(red,green,blue).rgb)
}

fun drawRandomImage(width: Int, height: Int, redRng: Int = 256, greenRng: Int = 256, blueRng: Int = 256) {
    val img = BufferedImage(width,height,TYPE_INT_RGB)

    for(posX in 0 until width){
        for (posY in 0 until height) {
            val r = if (redRng <=0) 0 else Random.nextInt(0, redRng)
            val g = if (greenRng <=0) 0 else Random.nextInt(0, greenRng)
            val b = if (blueRng <=0) 0 else Random.nextInt(0, blueRng)

            drawPixel(posX, posY, r, g, b, img)
        }
    }
    writeImage(img,"D:/randImage.bmp")
}