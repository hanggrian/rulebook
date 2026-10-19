package com.hanggrian.rulebook.checkstyle.checks;

class ParameterWrapping {
    void foo(Runnable bar) {
        foo(() -> {
            bar();
            bar();
        });
    }

    void bar() {}
}
