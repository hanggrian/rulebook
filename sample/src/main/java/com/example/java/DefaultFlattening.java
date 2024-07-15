package com.example.java;

public class DefaultFlattening {
    public void foo(final int bar) {
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
