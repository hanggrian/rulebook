package com.hanggrian.rulebook.checkstyle;

import java.util.function.Function;

class AssignmentWrap {
    void foo(Bar bar) {
        Function<Integer> bar = (a) -> {
            System.out.println(a);
        };
    }
}
