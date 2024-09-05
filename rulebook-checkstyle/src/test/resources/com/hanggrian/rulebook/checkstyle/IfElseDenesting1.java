package com.hanggrian.rulebook.checkstyle;

public class IfElseDenesting {
    public void foo() {
        if (true) {
        }
    }

    public void bar() {
        if (true) {
            baz();
        }
    }

    public void baz() {}
}
