package com.example.java;

import java.util.Arrays;

public class UnnecessaryParenthesesInLambda {
    public void foo() {
        Arrays
            .asList(1, 2, 3)
            .forEach(a -> {});
    }
}
