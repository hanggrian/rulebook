package com.hanggrian.rulebook.checkstyle;

class ParameterWrap {
    void foo(String a,
             int bz) {}

    void bar() {
        foo(new StringBuilder().toString(),
            1);
    }
}
