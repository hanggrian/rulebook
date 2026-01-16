package com.example.kotlin

class NestedIfElse {
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

    fun baz(): Int {
        println()
        return if (true) {
            println()
            0
        } else {
            0
        }
    }

    fun qux() {
        if (true) {
            try {
                if (false) {
                    println()
                    println()
                }
            } catch (e: Exception) {
                if (false) {
                    println()
                    println()
                }
            }
        }
        println()
    }
}
