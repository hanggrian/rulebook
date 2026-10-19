package com.example.kotlin

class LiteralComparison {
    fun foo(literal: Any?) {
        if (literal == null) {
            println()
        }
        if (1 == literal) {
            println()
        }
        if (literal is Boolean && !literal) {
            println()
        }
        if (0.0 == literal) {
            println()
        }
    }
}
