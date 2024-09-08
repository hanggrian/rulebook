package com.example.groovy

class ParameterWrapping {
    void foo(
        int a,
        int b
    ) {}

    void bar() {
        foo(
            0,
            1
        )
    }
}
