package com.hanggrian.rulebook.checkstyle.checks;

class NoLonelyCase {
    NoLonelyCase(int foo) {
        switch (foo) {
            case 0:
                bar();
                break;
        }
    }

    void bar() {}
}
