package com.hanggrian.rulebook.checkstyle.checks;

class ComplicatedSizeComparison {
    void foo(List<Integer> foo) {
        if (foo.size() == 0) {
        } else if (foo.size() > 0) {
        }
    }
}
