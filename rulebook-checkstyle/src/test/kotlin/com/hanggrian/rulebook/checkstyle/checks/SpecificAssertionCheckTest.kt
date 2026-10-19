package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class SpecificAssertionCheckTest : CheckTest() {
    override val check = SpecificAssertionCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Targeted assertion calls`() = assertAll("SpecificAssertion1")

    @Test
    fun `Generic assertion calls`() =
        assertAll(
            "SpecificAssertion2",
            "5:19: Use assertion 'assertEquals'.",
            "6:20: Use assertion 'assertNotEquals'.",
            "7:21: Use assertion 'assertTrue'.",
            "8:21: Use assertion 'assertNull'.",
        )
}
