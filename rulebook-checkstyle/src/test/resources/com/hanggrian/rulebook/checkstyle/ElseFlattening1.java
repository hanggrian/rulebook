package com.hanggrian.rulebook.checkstyle;

public class ElseFlattening {
    public void foo() {
        if (true) {
            baz();
        } else {
            baz();
        }
    }

    public void baz() {}
}
