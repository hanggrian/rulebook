package com.hanggrian.rulebook.checkstyle;

public class EmptyCodeBlockUnwrapping {
    public void foo() {
        try {
        } catch (Exception e) {
        }

        if (true) {
        } else if (false) {
        } else {
        }
    }
}
