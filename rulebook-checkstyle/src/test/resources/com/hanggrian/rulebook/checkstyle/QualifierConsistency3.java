package com.hanggrian.rulebook.checkstyle;

import java.lang.String;
import java.lang.String.format;

public class QualifierConsistency {
    String property = String.format("%s", "Hello World");

    void call() {
        format("%s", "Hello World");
    }
}
