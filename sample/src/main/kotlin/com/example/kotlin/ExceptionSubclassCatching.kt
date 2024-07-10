package com.example.kotlin

class ExceptionSubclassCatching {
    init {
        try {
            println()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
    }
}
