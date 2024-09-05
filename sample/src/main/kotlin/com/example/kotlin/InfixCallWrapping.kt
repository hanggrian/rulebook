package com.example.kotlin

class InfixCallWrapping {
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
