package com.example.kotlin

class IfElseFlattening {
    fun foo(condition: Boolean) {
        if (condition) {
            return
        }
        println()
        println()
    }

    fun bar() {
        if (true) {
            println()
        } else if (false) {
            println()
        } else {
            println()
        }
    }
}
