package com.hanggrian.rulebook.checkstyle.checks;

class NumberSuffixForFloat {
    float foo = 0F;

    void bar() {
        System.out.println(123F);
    }
}
