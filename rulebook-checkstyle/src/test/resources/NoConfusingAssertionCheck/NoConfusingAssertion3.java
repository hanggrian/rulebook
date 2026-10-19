package com.hanggrian.rulebook.checkstyle.checks;

class NoConfusingAssertion {
    private String s;

    void foo() {
        assertTrue(!s.isEmpty() && s.isBlank());
    }
}
