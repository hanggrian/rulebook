package com.hanggrian.rulebook.checkstyle;

import java.util.function.Function;

public class LambdaWrapping {
    public void foo() {
        bar(
            param -> {

                new StringBuilder()
                    .append("")
                    .toString();
            }
        );
    }

    public void bar(Function<Int, String> function) {}
}
