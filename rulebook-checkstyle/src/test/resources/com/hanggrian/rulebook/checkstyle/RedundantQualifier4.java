package com.hanggrian.rulebook.checkstyle;

import java.lang.String.format;

class RedundantQualifier {
    String property = java.lang.String.format("%s", "Hello World");

    void call() {
        java.lang.String.format("%s", "Hello World");
    }
}
