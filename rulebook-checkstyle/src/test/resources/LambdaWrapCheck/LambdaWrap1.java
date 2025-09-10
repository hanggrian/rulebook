package com.hanggrian.rulebook.checkstyle.checks;

import java.util.function.Function;

class LambdaWrap {
    void foo() {
        Function<Int, String> bar = param -> new StringBuilder().append("").toString();
        baz(param -> { new StringBuilder().append("").toString(); });
    }

    void baz(Function<Int, String> function) {}
}
