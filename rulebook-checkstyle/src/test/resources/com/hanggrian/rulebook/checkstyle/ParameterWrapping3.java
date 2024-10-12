package com.hanggrian.rulebook.checkstyle;

public class ParameterWrapping {
    public void foo(
        String a, int b
    ) {}

    public void bar() {
        foo(
            new StringBuilder()
                .toString(), 0
        );
    }
}
