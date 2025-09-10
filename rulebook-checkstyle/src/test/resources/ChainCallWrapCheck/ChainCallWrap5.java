package com.hanggrian.rulebook.checkstyle.checks;

class ChainCallWrap {
    void foo(Runnable r) {
        ChainCallWrapCheck().foo(() -> {
            System.out.println();
            System.out.println();
        });
    }
}
