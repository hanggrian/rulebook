package com.example.kotlin

class CaseSeparator {
    fun foo() {
        when (0) {
            // Lorem ipsum.
            0 -> println()

            /*
             * Lorem ipsum.
             */
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
