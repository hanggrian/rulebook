package com.hanggrian.rulebook.checkstyle;

class UnnecessarySwitch {
    UnnecessarySwitch(int foo) {
        switch (foo) {
            case 0:
                bar();
                break;
            case 1:
                bar();
                break;
        }
    }

    void bar() {}
}
