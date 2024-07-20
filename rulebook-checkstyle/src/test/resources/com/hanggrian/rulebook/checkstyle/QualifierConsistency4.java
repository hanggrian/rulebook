package com.hanggrian.rulebook.checkstyle;

import java.lang.String.format;

public class QualifierConsistency {
    String property = java.lang.String.format("%s", "Hello World");

    void call() {
        java.lang.String.format("%s", "Hello World");
    }
}
