package com.example.java;

import java.util.ArrayList;
import java.util.List;

public class TagsTrim<
        T
    > {
    public void foo() {
        List<?> arr =
            new ArrayList<
                Integer
            >();

        this.<
            String
        >bar();
    }

    public <T> void bar() {}
}
