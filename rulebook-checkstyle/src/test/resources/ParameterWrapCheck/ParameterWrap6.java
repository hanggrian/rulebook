package com.hanggrian.rulebook.checkstyle.checks;

class ParameterWrap {
    void foo(Runnable bar) {
        foo(() -> {
            bar();
            bar();
        });
    }

    void bar() {}
}
