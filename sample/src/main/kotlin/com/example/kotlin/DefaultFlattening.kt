package com.example.kotlin

class DefaultFlattening {
    fun foo(condition: Boolean) {
        when {
            true -> throw RuntimeException()
            false -> {
                return
            }
        }
        println()
        println()
    }
}
