package com.example.groovy

class SwitchMultipleBranching {
    public SwitchMultipleBranching(int foo, Runnable bar) {
        if (foo) {
            bar.run()
        }
        switch (foo) {
            case 0: bar.run()
            case 1:
                bar.run()
                break
        }
    }
}
