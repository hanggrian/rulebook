package com.example.kotlin

class IfFlattening {
    fun foo(condition: Boolean) {
        if (condition) {
            return
        }
        println()
        println()
    }
}
