package com.hanggrian.rulebook.checkstyle;

public class IfElseDenesting {
    public void foo() {
        if (true) {
            baz();
            baz();
        }

        // Lorem ipsum.
    }

    public void baz() {}
}
