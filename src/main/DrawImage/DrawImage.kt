package DrawImage

import FileDataHelper.FileDataHelper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.apache.poi.ss.usermodel.*
import java.awt.Color
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.TYPE_INT_RGB
import kotlin.random.Random
import javax.imageio.ImageIO

import java.io.File
import java.lang.Exception
import java.io.FileInputStream

import java.io.InputStream
import org.apache.poi.ss.usermodel.WorkbookFactory

import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.xssf.usermodel.XSSFColor
import org.apache.poi.hssf.util.HSSFColor
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardOpenOption


/*
* https://stackoverflow.com/questions/16497390/illegalargumentexception-color-parameter-outside-of-expected-range-red-green-b
* But color.getRed() (Blue, Green) can return a value up to 255. So you can get the following
*  // implementation 'org.apache.poi:poi:5.0.0'
*  // implementation 'org.apache.poi:poi-ooxml:5.0.0'
*/

fun main() {

    // prints pixel heart
    val map = arrayOf(
        listOf(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0),
        listOf(0,0,0,1,1,1,0,0,0,1,2,2,0,0,0),
        listOf(0,0,1,3,3,1,1,0,1,1,1,2,2,0,0),
        listOf(0,1,3,3,1,1,1,1,1,1,1,1,2,2,0),
        listOf(0,1,3,1,1,1,1,1,1,1,1,1,2,2,0),
        listOf(0,1,1,1,1,1,1,1,1,1,1,1,2,2,0),
        listOf(0,1,1,1,1,1,1,1,1,1,1,1,2,2,0),
        listOf(0,0,1,1,1,1,1,1,1,1,1,2,2,0,0),
        listOf(0,0,0,1,1,1,1,1,1,1,2,2,0,0,0),
        listOf(0,0,0,0,1,1,1,1,1,2,2,0,0,0,0),
        listOf(0,0,0,0,0,1,1,1,2,2,0,0,0,0,0),
        listOf(0,0,0,0,0,0,1,2,2,0,0,0,0,0,0),
        listOf(0,0,0,0,0,0,0,2,0,0,0,0,0,0,0),
        listOf(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0),
    )

    val arrl = arrayListOf<List<String>>()
    //writePixelColors("D:/pix.xlsx", "D:/pix.txt", "editor")
    runBlocking {
        matrix2D("D:/pix.txt","#")
            .map { line->
                println(line.map { toRGB(it) })
            }
    }

}

suspend fun matrix2D(file: String, delimiter: String): ArrayList<List<String>> {
    val list = arrayListOf<List<String>>()

    FileDataHelper().getContentAsync(file).use { matrix ->
        matrix.bufferedReader().lines().forEach { row ->
            list.add(
                row.split(delimiter)
                    .filter { it != "" }
            )
        }
    }
    return list
}

fun createPixelArray(file: String) {
    // create 2D array from file
}

fun drawIconFromText() {
    // load map of pixels from txt file
}

// Need refactor
// load data from xlsx and write to txt
// rewrite to async operation read data sheet
fun writePixelColors(input: String, output: String, listName: String) {
    val table = FileInputStream(input)
    val sheet = WorkbookFactory
                    .create(table)
                    .getSheet(listName)

    for (row: Row in sheet) {
        for (cell: Cell in row) {
            val cellStyle = cell.cellStyle
            val color = cellStyle.fillForegroundColorColor

            if (color != null) {
                when(color) {
                    is XSSFColor ->
                        FileDataHelper().writeContent(output, "#${color.argbHex.substring(2,8)}")
                    is HSSFColor ->
                        FileDataHelper().writeContent(output,"#${color.hexString.substring(2,8)}")
                }
            } //else FileDataHelper().writeContent(output, "# ERROR: Colors not found")

        }
    }
}

fun drawIcon(pixels: Array<List<Int>>, image: BufferedImage) {
    pixels.forEachIndexed { posY, row ->
        row.forEachIndexed { posX, col ->
            when(col) {
                1 -> drawTile(posX*10,posY*10,10,255,2,0, image) // red
                2 -> drawTile(posX*10,posY*10,10,156,25,31, image) // dark red
                3 -> drawTile(posX*10,posY*10,10,255,255,255, image) // violet
                else -> drawTile(posX*10,posY*10,10,23,0,44, image) // white
            }
        }
    }
}

fun writeImage(img: BufferedImage, file: String) {
    val imgthread = Thread(Runnable {
            ImageIO.write(img, File(file).extension, File(file))
        })
    try {
        imgthread.start()
    } catch (ex: Exception) {
        ex.printStackTrace()
        imgthread.interrupt()
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

fun drawRandImage(image: BufferedImage, stepSize: Int = 0, redRng: Int = 255, greenRng: Int = 255, blueRng: Int = 255) {
    for(posX in 0 until image.width step stepSize){
        for (posY in 0 until image.height step stepSize) {
            val r = if (redRng <=0) 0 else Random.nextInt(0, redRng)
            val g = if (greenRng <=0) 0 else Random.nextInt(0, greenRng)
            val b = if (blueRng <=0) 0 else Random.nextInt(0, blueRng)

            drawPixel(posX, posY, r, g, b, image)
        }
    }
}

val toRGB = { hex: String ->
    val blue: Int = hex.toInt(16) and 0xff
    val green: Int = hex.toInt(16) and 0xff00 shr 8
    val red: Int = hex.toInt(16) and 0xff0000 shr 16
    Color(red,green,blue)
}