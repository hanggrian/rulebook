package com.example.java;

public class DefaultDenesting {
    public void foo(int bar) {
        switch (bar) {
            case 0:
                throw new RuntimeException();
            case 1:
                return;
        }
        System.out.println();
        System.out.println();
    }
}
