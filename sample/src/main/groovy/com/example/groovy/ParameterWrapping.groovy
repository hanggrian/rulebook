package com.example.groovy

class ParameterWrapping {
    void foo(
        String a,
        int b
    ) {}

    void bar() {
        foo(
            new StringBuilder()
                .toString(),
            1
        )
    }
}
