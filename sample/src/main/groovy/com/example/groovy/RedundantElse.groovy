package com.example.groovy

class RedundantElse {
    def foo(var condition) {
        if (condition) {
            throw new IllegalStateException()
        } else if (!condition) {
            return
        }
        println()
        println()
    }
}
