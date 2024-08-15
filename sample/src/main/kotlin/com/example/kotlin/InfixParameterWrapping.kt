package com.example.kotlin

class InfixParameterWrapping {
    fun foo() {
        println(
            true ||
                false ||
                true,
        )
    }

    fun bar() {
        println(
            0 or
                1 or
                2,
        )
    }
}
