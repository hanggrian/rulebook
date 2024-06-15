package com.example.java;

import java.util.List;

public class IfStatementFlattening {
    public void iterate(final List<Integer> elements) {
        if (elements == null) {
            for (int element : elements) {
                System.out.println(element);
            }
        }
    }
}
