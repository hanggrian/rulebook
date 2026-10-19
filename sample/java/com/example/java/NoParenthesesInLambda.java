package com.example.java;

import java.util.Arrays;

public class NoParenthesesInLambda {
    public void foo() {
        Arrays
            .asList(1, 2, 3)
            .forEach(a -> {});
    }
}
