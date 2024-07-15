package com.hanggrian.rulebook.checkstyle;

public class ElseFlattening {
    public void foo() {
        if (true) {
            throw new Exception();
        } else if (false) {
            return;
        } else {
            baz();
        }
    }

    public void baz() {}
}
