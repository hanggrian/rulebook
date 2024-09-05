package com.hanggrian.rulebook.checkstyle;

public class ElseDenesting {
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
