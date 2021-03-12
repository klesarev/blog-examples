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
import java.io.InputStream

suspend fun main() {
    withContext(Dispatchers.Default) {
        val f = FileDataHelper.getContentAsync("D:/tesst.txt")
        when(f) {
            is Result.Success -> println(InputStreamReader(f.data as InputStream).readText())
            is Result.Error -> println(f.error::class.java)
        }
    }
}

sealed class Result<out T> {
    class Success<out T>(val data: T) : Result<T>()
    class Error<E>(val error: E): Result<E>()
}

class FileDataHelper {

    companion object {
        suspend fun getContentAsync(file: String): Result<Any> = supervisorScope {
            async(Dispatchers.IO) {
                try {
                    Result.Success(FileInputStream(file))
                } catch (ex: Exception) {
                    Result.Error(ex)
                }
            }.await()
        }

        suspend fun writeContentAsync(file: String, data: ByteArray, add: Boolean = false) = supervisorScope {
            async(Dispatchers.IO) {
                try {
                    FileOutputStream(file, add).write(data)
                } catch (ex: Exception) {
                    throw Exception("@ ${ex.message}")
                }
            }
        }
    }
}