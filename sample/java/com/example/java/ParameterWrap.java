package com.example.java;

public class ParameterWrap {
    public void foo(
        String a,
        int b
    ) {}

    public void bar() {
        foo(
            new StringBuilder()
                .toString(),
            0
        );
    }
}
