package com.hanggrian.rulebook.checkstyle.checks;

import java.lang.String.format;

class NoRedundantQualifier {
    String property = java.lang.String.format("%s", "Hello World");

    void call() {
        java.lang.String.format("%s", "Hello World");
    }
}
