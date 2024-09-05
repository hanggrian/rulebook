package com.example.groovy

class ElseDenesting {
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
