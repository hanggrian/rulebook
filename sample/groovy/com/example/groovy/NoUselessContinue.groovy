package com.example.groovy

class NoUselessContinue {
    def foo(int... items) {
        for (int item : items) {
            println('foo')
            // continue
        }
    }
}
