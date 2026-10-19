package com.hanggrian.rulebook.checkstyle.checks;

class SimpleSizeComparison {
    void foo(List<Integer> foo) {
        if (foo.size() == 0) {
        } else if (foo.size() > 0) {
        }
    }
}
