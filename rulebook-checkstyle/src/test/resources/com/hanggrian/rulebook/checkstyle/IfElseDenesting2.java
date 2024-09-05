package com.hanggrian.rulebook.checkstyle;

public class IfElseDenesting {
    public void foo() {
        if (true) {
            baz();
            baz();
        }
    }

    public void baz() {
        if (true) {
            baz(
                0
            );
        }
    }

    public void baz() {}
}
