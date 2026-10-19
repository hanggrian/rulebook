package com.example.kotlin

class NoRedundantDefault {
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
