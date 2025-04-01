package com.example.java;

public class RedundantElse {
    public void foo() {
        if (true) {
            throw new RuntimeException();
        }
        if (false) {
            return;
        }
        System.out.println();
        System.out.println();
    }
}
