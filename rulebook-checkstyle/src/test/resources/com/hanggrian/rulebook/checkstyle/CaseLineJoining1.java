package com.hanggrian.rulebook.checkstyle;

public class CaseLineJoining {
    public CaseLineJoining(int foo) {
        switch (foo) {
            case 0:
                bar();
                break;
            case 1:
                bar();
                break;
        }
    }

    public void bar() {}
}
