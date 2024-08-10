package com.example.kotlin

class IfElseFlattening {
    fun foo() {
        if (false) {
            return
        }
        if (false) {
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
