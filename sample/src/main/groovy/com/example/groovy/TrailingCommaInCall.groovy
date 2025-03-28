package com.example.groovy

class TrailingCommaInCall {
    def foo() {
        bar(
            1,
            2,
        )
    }

    def bar(int a, int b) {}
}
