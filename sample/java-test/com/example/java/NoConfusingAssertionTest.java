package com.example.java;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class NoConfusingAssertionTest {
    @Test
    public void test() {
        String s = "Hello World";
        assertFalse(s.isEmpty());
    }
}
