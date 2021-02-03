package Serialization

import kotlinx.serialization.*
import kotlinx.serialization.json.*

@Serializable
data class User(val name: String, val yearOfBirth: Int)

fun main() {
    // Serialization (Kotlin object to JSON string)

    val data = User("Louis", 1901)
    val string = Json.encodeToString(data)
    println(string) // {"name":"Louis","yearOfBirth":1901}

    // Deserialization (JSON string to Kotlin object)

    val obj = Json.decodeFromString<User>(string)
    println(obj) // User(name=Louis, yearOfBirth=1901)
}