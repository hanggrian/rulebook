package com.hanggrian.rulebook.checkstyle;

class ChainCallWrap {
    void foo(Runnable r) {
        ChainCallWrapCheck().foo(() -> {
            System.out.println();
            System.out.println();
        });
    }
}
