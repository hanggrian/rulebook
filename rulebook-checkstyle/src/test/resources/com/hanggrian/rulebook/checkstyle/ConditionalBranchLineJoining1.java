package com.hanggrian.rulebook.checkstyle;

public class ConditionalBranchLineJoining {
    public ConditionalBranchLineJoining() {
        switch (foo) {
            case 0:
                bar();
                break;
            case 1:
                baz();
                break;
        }
    }
}
