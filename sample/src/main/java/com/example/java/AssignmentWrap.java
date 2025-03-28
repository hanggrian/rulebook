package com.example.java;

public class AssignmentWrap {
    public void foo(Bar bar) {
        bar
            .baz =
            0 +
                1;
    }

    public static class Bar {
        int baz;
    }
}
