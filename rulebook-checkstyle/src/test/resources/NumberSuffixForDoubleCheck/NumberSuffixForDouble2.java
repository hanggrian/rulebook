package com.hanggrian.rulebook.checkstyle;

class NumberSuffixForDouble {
    float foo = 0D;

    void bar() {
        System.out.println(123D);
    }
}
