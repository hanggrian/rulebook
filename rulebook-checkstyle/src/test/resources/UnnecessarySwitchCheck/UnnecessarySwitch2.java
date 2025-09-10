package com.hanggrian.rulebook.checkstyle.checks;

class UnnecessarySwitch {
    UnnecessarySwitch(int foo) {
        switch (foo) {
            case 0:
                bar();
                break;
        }
    }

    void bar() {}
}
