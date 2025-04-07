package com.hanggrian.rulebook.checkstyle;

import java.lang.String;

class RedundantQualifier {
    String property = new String();

    void parameter(String param) {}

    void call() {
        String.format("%s", "Hello World");
    }
}
