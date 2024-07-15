package com.example.groovy

class ElseFlattening {
    void foo(boolean condition) {
        if (condition) {
            throw new IllegalStateException()
        } else if (!condition) {
            return
        }
        println()
        println()
    }
}
