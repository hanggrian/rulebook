package com.example.java;

public class LonelyCase {
    public LonelyCase(int foo, Runnable bar) {
        switch (foo) {
            case 0: bar.run();

            case 2: bar.run();

            default:
                bar.run();
                break;
        }
    }
}
