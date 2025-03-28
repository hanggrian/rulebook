package com.example.kotlin

class RedundantDefault {
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
