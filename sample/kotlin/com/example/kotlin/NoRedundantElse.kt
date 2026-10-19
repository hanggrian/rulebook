package com.example.kotlin

class NoRedundantElse {
    fun foo(condition: Boolean) {
        if (condition) {
            throw RuntimeException()
        }
        if (!condition) {
            return
        }
        println()
        println()
    }
}
