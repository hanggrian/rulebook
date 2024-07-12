package com.example.kotlin

class ElseFlattening {
    fun foo(condition: Boolean) {
        if (condition) {
            throw RuntimeException()
        }
        println()
        println()
    }
}
