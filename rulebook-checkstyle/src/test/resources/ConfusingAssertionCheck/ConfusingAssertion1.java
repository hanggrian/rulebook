package com.hanggrian.rulebook.checkstyle.checks;

class ConfusingAssertion {
    private String s;

    void foo() {
        assertTrue(s.isEmpty());
    }
}
