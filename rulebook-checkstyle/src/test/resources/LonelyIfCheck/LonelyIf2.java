package com.hanggrian.rulebook.checkstyle.checks;

class LonelyIf {
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
