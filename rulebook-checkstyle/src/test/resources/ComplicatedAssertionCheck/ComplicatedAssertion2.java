package com.hanggrian.rulebook.checkstyle.checks;

class ConfusingAssertion {
    void foo() {
        assertTrue(1 == 2);
        assertFalse(1 != 2);
        assertEquals(true, 1 == 2);
        assertEquals(null, 1);
    }
}
