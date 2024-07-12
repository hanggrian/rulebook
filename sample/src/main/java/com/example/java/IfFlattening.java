package com.example.java;

public class IfFlattening {
    public void foo() {
        if (true) {
            return;
        }
        System.out.println();
        System.out.println();
    }
}
