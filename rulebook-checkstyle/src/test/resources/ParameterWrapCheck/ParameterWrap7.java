package com.hanggrian.rulebook.checkstyle;

class ParameterWrap {
    void foo(Runnable bar) {
        foo(() -> {
            bar();
            bar();
        });
    }

    void bar() {}
}
