package com.hanggrian.rulebook.checkstyle.checks;

class LowercaseHex {
    float foo = 0x00bb00;

    void bar() {
        System.out.println(0xaa00cc);
    }
}
