package com.hanggrian.rulebook.checkstyle.checks;

import java.util.ArrayList;

class ComplicatedSizeEquality {
    void foo(Foo foo) {
        if (foo.bar.size() == 0) {
        }
    }

    class Foo {
        List<Integer> bar = new ArrayList<>();
    }
}
