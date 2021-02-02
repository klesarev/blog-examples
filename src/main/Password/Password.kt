package Password


fun main() {
    println(passGen(54))
}

fun passGen(symbols: Int): String {
    val letters = "abcdefghijklmnopqrstuvwxyz1234567890!@#$%^&*()-_+<>?:{}[]|"

    return letters
        .split("")
        .shuffled()
        .take(symbols)
        .joinToString("")
}
