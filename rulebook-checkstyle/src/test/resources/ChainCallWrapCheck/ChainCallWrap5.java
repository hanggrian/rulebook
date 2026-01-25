package com.hanggrian.rulebook.checkstyle.checks;

class ChainCallWrap {
    void foo(Runnable r) {
        new ChainCallWrapCheck().foo(() -> {
            System.out.println();
            System.out.println();
        });
    }
}
