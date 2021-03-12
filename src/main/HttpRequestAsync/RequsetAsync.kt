package HttpRequestAsync

import kotlinx.coroutines.runBlocking
import java.net.HttpURLConnection
import java.net.URI
import java.net.URL
import java.net.http.HttpClient
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import java.net.http.HttpResponse.BodyHandlers
import java.net.http.HttpRequest
import java.net.CookieStore




fun main() {

    println(
        getHttpAsync("https://jsonplaceholder.typicode.com/todos/1")
    )

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
                        // возвращаем результат если все ок
                        continuation.resume(inputStream.bufferedReader().readText())
                    } catch (ex: Exception) {
                        // возвращаем ошибку
                        continuation.resumeWithException(ex)
                    } finally {
                        disconnect()
                    }
                }

            }).start()
        }

    }
}


fun getHttpAsync(link: String): String? {
    val client = HttpClient.newBuilder().build()
    val request = HttpRequest.newBuilder()
        .uri(URI.create(link))
        .build()

    return client.sendAsync(request, BodyHandlers.ofString())
        .join()
        .body()
}