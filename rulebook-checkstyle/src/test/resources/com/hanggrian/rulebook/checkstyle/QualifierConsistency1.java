package com.hanggrian.rulebook.checkstyle;

import java.lang.String;

public class QualifierConsistency {
    String property = new String();

    void parameter(String param) {}

    void call() {
        String.format("%s", "Hello World");
    }
}
