package Serialization

import HttpRequest.RequestHelper
import HttpRequestAsync.RequsetAsync
import HttpRequestAsync.getHttpAsync
import kotlinx.serialization.*
import kotlinx.serialization.json.*

@Serializable
data class User(
    @SerialName("nick")
    val name: String,
    @SerialName("birthDate")
    val yearOfBirth: Int
    )

@Serializable
data class WPPost(
    val id: Int,
    val link: String,
    val slug: String,
    val tags: List<Int>
)

suspend fun main() {
    // Serialization (Kotlin object to JSON string)

    val data = User("Louis", 1991)
    val string = Json.encodeToString(data)
    println(string) // {"name":"Louis","yearOfBirth":1901}

    // Deserialization (JSON string to Kotlin object)
    val format = Json { ignoreUnknownKeys = true }
    val str = RequsetAsync().getDataAsync("https://fox-code.ru/wp-json/wp/v2/posts")

    val obj = format.decodeFromString<List<WPPost>>(str)

    obj.forEach {
        println(it)
    }
}