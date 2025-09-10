package com.hanggrian.rulebook.checkstyle.checks;

class ParameterWrap {
    void foo(String a, int b) {}

    void bar() {
        foo(new StringBuilder().toString(), 0);
    }
}
