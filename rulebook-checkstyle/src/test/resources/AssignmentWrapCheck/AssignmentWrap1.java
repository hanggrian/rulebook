package com.hanggrian.rulebook.checkstyle.checks;

import java.util.function.Function;

class AssignmentWrap {
    void foo() {
        int bar = 1 + 2;
    }

    void bar() {
        Function<Integer> baz = (a) -> System.out.println(a);
    }
}
