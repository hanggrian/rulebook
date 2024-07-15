package com.hanggrian.rulebook.checkstyle;

public class ElseFlattening {
    public void foo() {
        if (true) {
            baz();
        } else if (false) {
            baz();
        } else {
            baz();
        }
    }

    public void baz() {}
}
