package com.hanggrian.rulebook.checkstyle;

public class IfFlattening {
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
