package com.hanggrian.rulebook.checkstyle;

public class IfElseDenesting {
    public void foo() {
        if (true) {
            baz();
            baz();
        } else if (false) {
            baz();
        }
    }

    public void baz() {}
}
