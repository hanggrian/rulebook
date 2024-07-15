package com.hanggrian.rulebook.checkstyle;

public class IfElseFlattening {
    public void foo() {
        if (true) {
            baz();
            baz();
        }

        // Lorem ipsum.
    }

    public void baz() {}
}
