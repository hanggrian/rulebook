package com.hanggrian.rulebook.checkstyle.checks;

import java.lang.String;
import java.lang.String.format;

class NoRedundantQualifier {
    String property = String.format("%s", "Hello World");

    void call() {
        format("%s", "Hello World");
    }
}
