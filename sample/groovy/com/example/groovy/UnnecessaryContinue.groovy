package com.example.groovy

class UnnecessaryContinue {
    def foo(int... items) {
        for (int item : items) {
            println('foo')
            // continue
        }
    }
}
