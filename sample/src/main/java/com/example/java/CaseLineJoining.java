package com.example.java;

public class CaseLineJoining {
    public CaseLineJoining(int foo, Runnable bar, Runnable baz) {
        switch (foo) {
            case 0: bar.run();
            case 1:
                bar.run();
                break;
            default:
                baz.run();
                break;
        }
    }
}
