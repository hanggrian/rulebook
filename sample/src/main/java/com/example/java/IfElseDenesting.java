package com.example.java;

public class IfElseDenesting {
    public void foo() {
        if (true) {
            return;
        }
        System.out.println();
        System.out.println();
    }

    public void bar() {
        if (true) {
            System.out.println();
        } else if (false) {
            if (false) {
                System.out.println();
            }
        } else {
            if (true) {
                System.out.println();
            }
        }
    }
}
