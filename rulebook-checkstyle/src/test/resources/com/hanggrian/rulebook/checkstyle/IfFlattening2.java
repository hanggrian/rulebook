package com.hanggrian.rulebook.checkstyle;

public class IfFlattening {
    public void foo() {
        if (true) {
            baz();
            baz();
        }
    }

    public void baz() {}
}
