package FileDataHelper

import kotlinx.coroutines.*
import java.io.*
import java.lang.Exception
import java.io.InputStream

suspend fun main() {
    withContext(Dispatchers.Default) {
        val f = FileDataHelper.getContentAsync("D:/test.txt")
        when(f) {
            is Result.Success -> println(InputStreamReader(f.data as InputStream).readText())
            is Result.Error -> {
                FileDataHelper.writeContentAsync("D:/tesst.txt","no data allowed".toByteArray())
            }
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
                    if (isActive) {
                        FileOutputStream(file, add).write(data)
                    } else return@async
                } catch (ex: Exception) {
                    throw Exception("@ ${ex.message}")
                }
            }.await()
        }
    }
}