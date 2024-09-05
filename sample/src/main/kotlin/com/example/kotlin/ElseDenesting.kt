package com.example.kotlin

class ElseDenesting {
    fun foo(condition: Boolean) {
        if (condition) {
            throw RuntimeException()
        } else if (!condition) {
            return
        }
        println()
        println()
    }
}
