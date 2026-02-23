package com.example.kotlin

class UnnecessaryBraces {
    fun foo(bar: Int) {
        if (bar == 0) {
            println()
        } else if (bar == 1) {
            println()
            println()
        }
        println()
    }
}
