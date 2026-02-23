package com.example.groovy

class UnnecessaryBraces {
    def foo(int bar) {
        if (bar == 0) {
            println()
        } else if (bar == 1) {
            println()
            println()
        }
        println()
    }
}
