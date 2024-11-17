package com.example.kotlin

class CaseLineSpacing {
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
