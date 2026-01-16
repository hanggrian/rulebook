package com.example.kotlin

class InfixCallWrap {
    fun foo() {
        println(
            0 or
                1 or
                2,
        )
    }
}
