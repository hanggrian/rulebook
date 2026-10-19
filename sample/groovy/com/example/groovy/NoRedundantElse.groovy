package com.example.groovy

class NoRedundantElse {
    def foo(var condition) {
        if (condition) {
            throw new IllegalStateException()
        }
        if (!condition) {
            return
        }
        println()
        println()
    }
}
