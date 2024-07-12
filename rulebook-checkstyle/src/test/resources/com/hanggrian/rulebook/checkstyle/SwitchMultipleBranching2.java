package com.hanggrian.rulebook.checkstyle;

public class SwitchMultipleBranching {
    public SwitchMultipleBranching(int foo) {
        switch (foo) {
            case 0:
                bar();
                break;
        }
    }

    public void bar() {}
}
