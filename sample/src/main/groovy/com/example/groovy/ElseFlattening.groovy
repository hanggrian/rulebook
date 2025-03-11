package com.example.groovy

class ElseFlattening {
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
