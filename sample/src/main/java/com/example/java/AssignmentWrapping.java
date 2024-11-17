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
        // if (bar.baz == 1) System.out.println();
        int b =
            bar.baz == a
                ? a
                : 0;
    }

    public static class Bar {
        int baz;
    }
}
