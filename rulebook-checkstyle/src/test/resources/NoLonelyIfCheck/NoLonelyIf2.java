package com.hanggrian.rulebook.checkstyle.checks;

class NoLonelyIf {
    void foo() {
        if (true) {
            System.out.println();
        } else {
            if (false) {
                System.out.println();
            }
        }
    }
}
