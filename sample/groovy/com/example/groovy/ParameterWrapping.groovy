package com.example.groovy

class ParameterWrapping {
    def foo(
        var a,
        var b
    ) {}

    def bar() {
        foo(
            new StringBuilder()
                .toString(),
            1,
        )
    }
}
