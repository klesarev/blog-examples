package HttpRequestAsync

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking
import java.net.HttpURLConnection
import java.net.URL
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

fun main() {

    runBlocking {
        val data = RequsetAsync().getDataAsync("https://jsonplaceholder.typicode.com/todos/1")
        println(data)
    }

}

class RequsetAsync {

    private val userAgentHeader = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
            "AppleWebKit/537.36 (KHTML, like Gecko) " +
            "Chrome/87.0.4280.141 Safari/537.36"

    suspend fun getDataAsync(url: String, userAgent: String = userAgentHeader): String {

        return suspendCoroutine { continuation ->
            Thread(kotlinx.coroutines.Runnable {

                with(URL(url).openConnection() as HttpURLConnection){
                    addRequestProperty("User-Agent", userAgent)
                    try {
                        continuation.resume(inputStream.bufferedReader().readText())
                    } catch (ex: Exception) {
                        continuation.resumeWithException(ex)
                    } finally {
                        disconnect()
                    }
                }

            }).start()
        }

    }
}