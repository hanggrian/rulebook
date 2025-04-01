package com.example.java;

import java.util.function.Consumer;

public class AssignmentWrap {
    public void foo(Bar bar) {
        bar
            .baz =
            0 +
                1;

        Consumer<Integer> baz = i -> {
            System.out.println(i);
            System.out.println(i);
        };

        Consumer<Integer> qux = i ->
            System.out.println(i);
    }

    public static class Bar {
        int baz;
    }
}
