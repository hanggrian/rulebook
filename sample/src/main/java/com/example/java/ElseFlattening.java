package com.example.java;

public class ElseFlattening {
    public void foo() {
        if (true) {
            throw new RuntimeException();
        }
        System.out.println();
        System.out.println();
    }
}
