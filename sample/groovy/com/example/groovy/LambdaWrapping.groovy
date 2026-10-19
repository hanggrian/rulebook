package com.example.groovy

class LambdaWrapping {
    def foo() {
        new Thread(() -> println(0))
    }

    def bar() {
        new Thread(
            () ->
                System
                    .out
                    .println(0),
        )
    }

    def baz() {
        new Thread(
            () -> {
                System
                    .out
                    .println(0)
            },
        )
    }
}
