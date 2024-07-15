package com.hanggrian.rulebook.checkstyle;

public class IfElseFlattening {
    public void foo() {
        if (true) {
            baz();
        } else {
            baz();
        }
    }

    public void baz() {}
}
