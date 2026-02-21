package com.hanggrian.rulebook.checkstyle.checks;

class LowercaseHex {
    float foo = 0X00BB00;

    void bar() {
        System.out.println(0XAA00CC);
    }
}
