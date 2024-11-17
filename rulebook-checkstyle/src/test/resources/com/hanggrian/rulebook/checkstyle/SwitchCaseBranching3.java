package com.hanggrian.rulebook.checkstyle;

public class SwitchCaseBranching {
    public SwitchCaseBranching(int foo) {
        switch (foo) {
            case 1:
            case 2:
                bar();
                break;
        }
    }

    public void bar() {}
}
