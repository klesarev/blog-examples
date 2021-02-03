package FileDataHelper

import kotlinx.coroutines.*
import java.io.*
import java.lang.Exception
import java.lang.Runnable
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

fun main() {
    runBlocking {
        GlobalScope.launch {
           val txt =  FileDataHelper().getContentAsync("D:/async.txt").bufferedReader().readText()
            println(txt)
        }.join()
    }
}

class FileDataHelper {

    suspend fun getContent(source: String): String {
        lateinit var reader: BufferedReader

        return suspendCoroutine { continuation ->
            Thread(Runnable{
                try {
                    reader = Files.newBufferedReader(Paths.get(source))
                    continuation.resume(reader.use { it.readText() })
                } catch (error: IOException) {
                    error.printStackTrace()
                    continuation.resumeWithException(error)
                }
            }).start()
        }
    }

    fun writeContent(source: String, content: String) {
           try {
               Files.write(Paths.get(source), content.toByteArray(),
                   StandardOpenOption.APPEND,
                   StandardOpenOption.CREATE
               )
           } catch (error: IOException) {
               error.printStackTrace()
           }
    }

    suspend fun writeContentAsync(file: String, data: ByteArray, add: Boolean = true) = coroutineScope {
        val dataStr = async(Dispatchers.IO) {
            FileOutputStream(file, add).write(data)
        }
        try {
            dataStr.await()
        } catch (ex: Exception) {
            ex.printStackTrace()
            throw Exception("Error: ${ex.message}")
        }
    }

    suspend fun getContentAsync(source: String): InputStream = coroutineScope {
        val dataStr = async(Dispatchers.IO) { FileInputStream(source) }
        try {
            dataStr.await()
        } catch (ex: Exception) {
            throw Exception("Error: ${ex.message}")
        }
    }

}