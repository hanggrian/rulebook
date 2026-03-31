package com.example.kotlin

import kotlin.test.Test
import kotlin.test.assertFalse

class ConfusingAssertionTest {
    @Test
    fun test() {
        val s = "Hello World"
        assertFalse(s.isEmpty())
    }
}
