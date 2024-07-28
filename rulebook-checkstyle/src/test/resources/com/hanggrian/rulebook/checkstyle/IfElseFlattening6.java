package com.hanggrian.rulebook.checkstyle;

public class IfElseFlattening {
    public void foo() {
        if (true) {
            if (true) {
                baz();
                baz();
            }
        }
    }

    public void bar() {
        if (true) {
            baz();
        } else {
            if (true) {
                baz();
                baz();
            }
        }
    }

    public void baz() {}
}
