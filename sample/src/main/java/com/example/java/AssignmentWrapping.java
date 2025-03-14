package com.example.java;

public class AssignmentWrapping {
    public void foo(Bar bar) {
        bar
            .baz =
            0 +
                1;

        int a =
            1 +
                2;
    }

    public static class Bar {
        int baz;
    }
}
