package com.example.java;

public class ParameterWrapping {
    public void foo(
        int a,
        int b
    ) {}

    public void bar() {
        foo(
            0,
            1
        );
    }
}
