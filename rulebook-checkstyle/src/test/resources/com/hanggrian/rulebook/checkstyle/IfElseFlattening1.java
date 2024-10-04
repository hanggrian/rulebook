package com.hanggrian.rulebook.checkstyle;

public class IfElseFlattening {
    public void foo() {
        if (true) {
        }
    }

    public void bar() {
        if (true) {
            baz();
        }
    }

    public void baz() {}
}
