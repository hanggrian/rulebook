package com.example.kotlin

class CaseLineSpacing {
    fun foo() {
        when (0) {
            0 -> println()
            1 -> {
                println()
            }
        }
    }

    fun bar() {
        when (0) {
            0 -> println()
            // Lorem ipsum.
            1 -> {
                println()
            }

            /** Lorem ipsum. */
            else -> {
                println()
            }
        }
    }
}
