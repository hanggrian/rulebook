package com.example.java;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class ConfusingAssertionTest {
    @Test
    public void test() {
        String s = "Hello World";
        assertFalse(s.isEmpty());
    }
}
