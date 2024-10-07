package com.example.kotlin

class InfixCallWrapping {
    fun foo() {
        println(
            0 or
                1 or
                2,
        )
    }
}
