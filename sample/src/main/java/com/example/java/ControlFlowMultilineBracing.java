package com.example.java;

public class ControlFlowMultilineBracing {
    public void foo() {
        if (true) {
            System.out.println();
        }

        if (true) {
            System.out.println();
        } else if (true) {
            System.out.println();
        } else {
            System.out.println();
        }

        for (int i : new int[]{}) {
            bar();
        }
    }

    public void bar() {}

    public void baz() {}
}
