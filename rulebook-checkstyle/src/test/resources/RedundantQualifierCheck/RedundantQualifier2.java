package com.hanggrian.rulebook.checkstyle.checks;

import java.lang.String;

class RedundantQualifier {
    java.lang.String property = new java.lang.String();

    void parameter(java.lang.String param) {}

    void call() {
        java.lang.String.format("%s", "Hello World");
    }
}
