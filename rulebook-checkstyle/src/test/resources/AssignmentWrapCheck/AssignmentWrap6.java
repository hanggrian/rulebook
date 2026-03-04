package com.hanggrian.rulebook.checkstyle.checks;

import java.util.function.Function;

class AssignmentWrap {
    void var(Bar bar) {
        Function<Integer> baz =
            (a) -> System.out.println(a);
    }
}
