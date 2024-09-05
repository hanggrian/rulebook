package com.example.kotlin

class DefaultDenesting {
    fun foo(condition: Boolean) {
        when {
            true -> throw RuntimeException()
            false -> {
                return
            }
        }
        println()
        println()
    }
}
