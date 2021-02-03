package FileDataHelper

import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.IOException
import java.lang.Runnable
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

suspend fun main() {
    FileDataHelper().writeContent("D:/tt.txt","From dispatchers")
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

   suspend fun writeContent(source: String, content: String) = coroutineScope {
       Thread (Runnable{
           try {
               Files.write(Paths.get(source), content.toByteArray(),
                   StandardOpenOption.APPEND,
                   StandardOpenOption.CREATE
               )
           } catch (error: IOException) {
               error.printStackTrace()
           }
       }).start()
   }

}