package DrawImage

import java.awt.color.ColorSpace
import java.awt.image.BufferedImage
import java.awt.image.ColorConvertOp





fun BufferedImage.desaturate(): BufferedImage {
    val colorConvert = ColorConvertOp (ColorSpace.getInstance(ColorSpace.CS_GRAY), null)
    return colorConvert.filter(this, this)
}

//fun BufferedImage.channelDisable(redLvl: Int) {
//    for (X in 0 until this.width) {
//        for (Y in 0 until this.height) {
//            val rgb: Int = this.getRGB(X, Y) // off the black line
//
//            val red = rgb shr 16 and 0xFF
//            val green = rgb shr 8 and 0xFF
//            val blue = rgb and 0xFF
//
//            drawPixel(X,Y,redLvl,green,blue,this)
//        }
//    }
//}