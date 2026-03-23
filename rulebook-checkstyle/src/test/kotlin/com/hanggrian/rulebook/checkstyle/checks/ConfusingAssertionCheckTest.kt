package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class ConfusingAssertionCheckTest : CheckTest() {
    override val check = ConfusingAssertionCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Positive assertion calls`() = assertAll("ConfusingAssertion1")

    @Test
    fun `Negative assertion calls`() =
        assertAll(
            "ConfusingAssertion2",
            "7:19: Omit negation and replace call with 'assertFalse'.",
        )

    @Test
    fun `Skip chained conditions`() = assertAll("ConfusingAssertion3")
}
