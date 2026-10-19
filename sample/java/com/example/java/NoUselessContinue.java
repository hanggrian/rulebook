package com.example.java;

public class NoUselessContinue {
    public void foo(int... items) {
        for (int item : items) {
            System.out.println("foo");
            // continue;
        }
    }
}
