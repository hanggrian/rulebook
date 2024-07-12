package com.hanggrian.rulebook.checkstyle;

public class IfFlattening {
    public void foo() {
        if (true) {
            baz();
            baz();
        } else {
            baz();
        }
    }

    public void baz() {}
}
