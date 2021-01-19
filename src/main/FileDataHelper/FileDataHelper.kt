package FileDataHelper

import java.io.BufferedReader
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

class FileDataHelper {
    fun getContent(source: String): String {
        var result = ""
        try {
            val buffReader: BufferedReader = Files.newBufferedReader(Paths.get(source))
            result = buffReader.use { it.readText() }
        } catch (error: IOException) {
            error.printStackTrace()
        }
        return result
    }

    fun writeContent(source: String, content: String) {
        try {
            Files.write(Paths.get(source), content.toByteArray(), StandardOpenOption.APPEND, StandardOpenOption.CREATE)
        } catch (error: IOException) {
            error.printStackTrace()
        }
    }
}