package com.example.java;

public class LambdaWrapping {
    public void foo() {
        new Thread(() -> System.out.println(0));
    }

    public void bar() {
        new Thread(
            () ->
                System.out.println(0)
        );
    }

    public void baz() {
        new Thread(
            () -> {
                System
                    .out
                    .println(0);
            }
        );
    }
}
