package com.hanggrian.rulebook.checkstyle;

public class IfElseFlattening {
    public void foo() {
        if (true) {
            baz();
            baz();
        }
    }

    public void baz() {}
}
