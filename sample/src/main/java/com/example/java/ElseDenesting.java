package com.example.java;

public class ElseDenesting {
    public void foo() {
        if (true) {
            throw new RuntimeException();
        } else if (false) {
            return;
        }
        System.out.println();
        System.out.println();
    }
}
