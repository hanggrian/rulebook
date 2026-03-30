package com.hanggrian.rulebook.checkstyle.checks;

class ConfusingAssertion {
    void foo() {
        assertEquals(1, 2);
        assertNotEquals(1, 2);
        assertTrue(false);
        assertFalse(1);
    }
}
