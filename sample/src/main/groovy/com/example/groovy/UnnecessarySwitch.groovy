package com.example.groovy

class UnnecessarySwitch {
    public UnnecessarySwitch(var foo, var bar) {
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
