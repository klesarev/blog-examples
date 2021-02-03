package HttpRequest

import HttpRequestAsync.getHttpAsync
import kotlinx.coroutines.*
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * @author Mr.Fox (fox-code.ru)
 * RequestHelper.getData()
    * @param url ссылка в виде сткроки
    * @param userAgent свойство UserAgent, по умолчанию не пустое, можно указать свое
    * @return возвращает страницу в виде строки
 * */

fun main() {
    println(
        RequestHelper().getData("http://127.0.0.1:8080/api?language=Kotlin&level=middle&port=8080")
    )
}

class RequestHelper {
    private val userAgentHeader = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
            "AppleWebKit/537.36 (KHTML, like Gecko) " +
            "Chrome/87.0.4280.141 Safari/537.36"

    fun getData(url: String, userAgent: String = userAgentHeader): String {
        lateinit var text: String

        with(URL(url).openConnection() as HttpURLConnection) {
            addRequestProperty("User-Agent", userAgent)

            try {
                text = inputStream.bufferedReader().readText()
            } catch (ex: Exception) {
                text = """{"result" : "Can't get data from url", "url" : "${url}", "message" : "${ex.localizedMessage}"}"""
            } finally {
                disconnect()
            }

        }
        return text
    }

}
