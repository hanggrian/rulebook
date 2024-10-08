package com.hanggrian.rulebook.checkstyle;

public class AssignmentWrapping {
    public void foo(Bar bar) {
        bar
            .baz = 0;
    }

    public static class Bar {
        int baz;
    }
}
