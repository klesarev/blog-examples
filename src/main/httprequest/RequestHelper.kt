package httprequest

import java.net.HttpURLConnection
import java.net.URL

/**
 * @author Mr.Fox (fox-code.ru)
 * RequestHelper.getData()
    * @param url ссылка в виде сткроки
    * @param userAgent свойство UserAgent, по умолчанию не пустое, можно указать свое
    * @return возвращает страницу в виде строки
 * */

fun main() {

    println(
        RequestHelper().getData("https://jsonplaceholder.typicode.com/todos/1" )
    )

}

class RequestHelper {
    private val userAgentHeader = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
            "AppleWebKit/537.36 (KHTML, like Gecko) " +
            "Chrome/87.0.4280.141 Safari/537.36"

    fun getData(url: String, userAgent: String = userAgentHeader): String {
        val sb = StringBuilder()

        with(URL(url).openConnection() as HttpURLConnection) {
            addRequestProperty("User-Agent", userAgent)

            try {
                val text = inputStream.bufferedReader().readText()
                sb.append(text)
            } catch (ex: Exception) {
                ex.printStackTrace()
            } finally {
                disconnect()
            }

        }
        return sb.toString()
    }
}