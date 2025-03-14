package com.example.groovy

class SwitchCaseBranching {
    public SwitchCaseBranching(var foo, var bar) {
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
