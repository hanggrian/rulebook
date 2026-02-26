package com.example.java;

public class UnnecessaryContinue {
    public void foo(int... items) {
        for (int item : items) {
            System.out.println("foo");
            // continue;
        }
    }
}
