package com.hanggrian.rulebook.checkstyle;

public class ElseFlattening {
    public void foo() {
        if (true) {
            return;
        } else {
            baz();
        }
    }

    public void baz() {}
}
