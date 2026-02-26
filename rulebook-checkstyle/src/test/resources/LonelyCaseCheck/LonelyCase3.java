package com.hanggrian.rulebook.checkstyle.checks;

class LonelyCase {
    LonelyCase(int foo) {
        switch (foo) {
            case 1:
            case 2:
                bar();
                break;
        }
    }

    void bar() {}
}
