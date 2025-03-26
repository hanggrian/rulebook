package com.hanggrian.rulebook.checkstyle;

public class IfElseFlattening {
    public void foo() {
        if (true) {
            if (true) {
                baz();
                baz();
            }
        }
        baz();
    }

    public void baz() {}
}
