package HttpServerMinimal

import com.sun.net.httpserver.HttpServer
import java.io.OutputStream
import java.net.InetSocketAddress
import java.util.concurrent.Executors
import java.util.concurrent.ThreadPoolExecutor

val threadPool = Executors.newFixedThreadPool(10) as ThreadPoolExecutor

fun main(args: Array<String>) {
    simpleServer(8080)
}

fun simpleServer(port: Int) {
    HttpServer.create(InetSocketAddress(port), 0).apply {

        setExecutor(threadPool);
        println("Server runs at: 127.0.0.1:${address.port}")

        createContext("/api") { http ->

            when(http.requestMethod) {
                "GET" -> {
                    http.responseHeaders.add("Content-type", "text/plain")
                    http.sendResponseHeaders(200, 0)
                    val outputStream: OutputStream = http.responseBody
                    if (http.requestURI.toString().indexOf("?") > 0) {
                        outputStream.write(getParams(http.requestURI.toString()).toString().encodeToByteArray())
                        outputStream.flush()
                    } else {
                        outputStream.write("Hello from server!".encodeToByteArray())
                        outputStream.flush()
                    }
                    http.close()
                }
                "POST" -> { http.sendResponseHeaders(405, 0) }
            }

        }
        start()
    }
}

fun getParams(paramString: String): Map<String,String> {
    val params = mutableMapOf<String,String>()

    paramString
        .substring(paramString.indexOf("?")+1, paramString.length)
        .split(Regex("&"))
        .forEach {
            params.put(it.split("=")[0],it.split("=")[1])
        }

    return params.toMap()
}