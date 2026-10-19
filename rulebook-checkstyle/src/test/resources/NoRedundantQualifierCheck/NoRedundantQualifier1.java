package com.hanggrian.rulebook.checkstyle.checks;

import java.lang.String;

class NoRedundantQualifier {
    String property = new String();

    void parameter(String param) {}

    void call() {
        String.format("%s", "Hello World");
    }
}
