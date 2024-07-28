package com.example.java;

public class SwitchMultipleBranching {
    public SwitchMultipleBranching(int foo, Runnable bar) {
        if (foo == 0) {
            bar.run();
        }
        switch (foo) {
            case 0: bar.run();
            case 1:
                bar.run();
                break;
        }
    }
}
