package com.example.kotlin

class ElseFlattening {
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
