package com.example

fun alert(message: String) {
    if (message.isEmpty()) {
        return
    }
    val s = message.trim()
    println(s)
}

fun alert2(message: String) {
    if (message.isNotEmpty()) {
        var a = 1 + 2
        a += 0
    } else if (message.isNotBlank()) {
        var b = 3 + 4
        b += 0
    } else {
        var c = 5 + 6
        c += 0
    }
}
