package com.example.kotlin

class DeprecatedIdentity {
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
