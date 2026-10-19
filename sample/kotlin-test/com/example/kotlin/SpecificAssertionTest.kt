package com.example.kotlin

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class SpecificAssertionTest {
    @Test
    fun test() {
        assertEquals("Hello", "World")

        assertTrue(false)

        val s: String? = "Hello"
        assertNull(null, s)
    }
}
