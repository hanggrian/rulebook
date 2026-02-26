package com.hanggrian.rulebook.checkstyle.checks;

class LonelyCase {
    LonelyCase(int foo) {
        switch (foo) {
            case 0:
                bar();
                break;
        }
    }

    void bar() {}
}
