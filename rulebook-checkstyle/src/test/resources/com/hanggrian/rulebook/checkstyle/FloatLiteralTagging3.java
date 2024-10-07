package com.hanggrian.rulebook.checkstyle;

public class FloatLiteralTagging {
    float foo = 0x00f;

    void bar() {
        System.out.println(0x123f);
    }
}
