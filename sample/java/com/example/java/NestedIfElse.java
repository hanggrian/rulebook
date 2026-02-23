package com.example.java;

public class NestedIfElse {
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
            System.out.println();
            System.out.println();
        }
    }

    public void baz() {
        try {
            if (true) {
                System.out.println();
                System.out.println();
            }
        } catch (RuntimeException e) {
            System.out.println();
        }
    }
}
