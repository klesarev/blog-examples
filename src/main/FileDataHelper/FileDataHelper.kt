package FileDataHelper

import kotlinx.coroutines.*
import org.apache.commons.compress.utils.IOUtils.toByteArray
import org.apache.commons.io.IOUtils.toInputStream
import java.io.*
import java.lang.Exception
import java.lang.Runnable
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import java.io.InputStream
import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets


suspend fun main() {
    GlobalScope.launch {
        val data = FileDataHelper().getContentAsync("D:/tess.txt")
        println(InputStreamReader(data).readLines())
    }.join()
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

    suspend fun writeContentAsync(file: String, data: ByteArray, add: Boolean = false) = coroutineScope {
        supervisorScope {
            val dataStr = async(Dispatchers.IO) {
                FileOutputStream(file, add).write(data)
            }
            try {
                dataStr.await()
            } catch (ex: Exception) {
                throw Exception("@ ${ex.message}")
            }
        }
    }

    suspend fun getContentAsync(file: String): InputStream = coroutineScope {
        // supervisorScope отменяет только текущую корутину
        supervisorScope {
            val dataStr = async(Dispatchers.IO) {
                FileInputStream(file)
            }
            try {
                dataStr.await()
            } catch (ex: Exception) {
                throw Exception("@ ${ex.message}")
            }
        }
    }

}