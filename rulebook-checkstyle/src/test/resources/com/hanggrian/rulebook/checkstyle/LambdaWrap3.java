package com.hanggrian.rulebook.checkstyle;

import java.util.function.Function;

class LambdaWrap {
    void foo() {
        bar(
            param -> new StringBuilder()
                .append("")
                .toString()
        );
    }

    void bar(Function<Int, String> function) {}
}
