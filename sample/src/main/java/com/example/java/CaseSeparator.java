package com.example.java;

public class CaseSeparator {
    public CaseSeparator(int foo, Runnable bar, Runnable baz) {
        switch (foo) {
            // Lorem ipsum.
            case 0:
                bar.run();

            /* Lorem ipsum. */
            case 1:
                bar.run();
                break;

            /* Lorem ipsum. */
            default:
                baz.run();
                break;
        }
    }
}
