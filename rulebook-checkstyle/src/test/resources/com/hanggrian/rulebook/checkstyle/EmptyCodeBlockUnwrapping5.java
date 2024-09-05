package com.hanggrian.rulebook.checkstyle;

public class EmptyCodeBlockUnwrapping {
    public void foo() {
        if (true) {
        } else if (false) {
        } else {
        }
    }

    public void bar() {
        try {
        } catch (Exception e) {
        }
    }
}
