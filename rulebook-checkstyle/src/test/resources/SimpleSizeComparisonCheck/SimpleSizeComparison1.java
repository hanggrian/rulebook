package com.hanggrian.rulebook.checkstyle.checks;

class SimpleSizeComparison {
    void foo(List<Integer> foo) {
        if (foo.isEmpty()) {
        } else if (!foo.isEmpty()) {
        }
    }
}
