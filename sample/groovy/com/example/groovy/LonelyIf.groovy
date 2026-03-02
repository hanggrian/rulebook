package com.example.groovy

class LonelyIf {
    def foo(int bar) {
        if (bar.is(0)) {
            println()
        } else if (bar.is(1)) {
            println()
            println()
        }
        println()
    }
}
