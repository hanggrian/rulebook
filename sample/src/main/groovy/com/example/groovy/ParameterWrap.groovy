package com.example.groovy

class ParameterWrap {
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
