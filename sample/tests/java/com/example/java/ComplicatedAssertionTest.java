package com.example.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ComplicatedAssertionTest {
    @Test
    public void test() {
        assertEquals("Hello", "World");

        assertTrue(false);

        String s = "Hello";
        assertNull(null, s);
    }
}
