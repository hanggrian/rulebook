package com.hanggrian.rulebook.checkstyle;

import java.util.function.Function;

class AssignmentWrap {
    void foo(Bar bar) {
        Function<Integer> bar = (a) -> {
            System.out.println(a);
        };
        Function<Integer> baz = (a) ->
            System.out.println(a);
    }
}
