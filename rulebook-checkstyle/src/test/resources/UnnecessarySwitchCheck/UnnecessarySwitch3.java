package com.hanggrian.rulebook.checkstyle;

class UnnecessarySwitch {
    UnnecessarySwitch(int foo) {
        switch (foo) {
            case 1:
            case 2:
                bar();
                break;
        }
    }

    void bar() {}
}
