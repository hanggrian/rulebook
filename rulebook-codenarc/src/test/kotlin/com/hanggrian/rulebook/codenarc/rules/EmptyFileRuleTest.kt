package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.RuleTest
import kotlin.test.Test

class EmptyFileRuleTest : RuleTest<EmptyFileRule>() {
    override fun createRule() = EmptyFileRule()

    @Test
    fun `Rule properties`() = rule.assertProperties()

    @Test
    fun `File with code`() =
        assertNoViolations(
            """
            def foo() {
                var baz = 0
            }
            """.trimIndent(),
        )

    @Test
    fun `File without code`() =
        assertSingleViolation(
            "\n\n",
            0,
            "",
            "Delete the empty file.",
        )

    @Test
    fun `Package is stripped`() =
        assertSingleViolation(
            "package com.example\n",
            0,
            "",
            "Delete the empty file.",
        )

    @Test
    fun `Skip comments`() {
        assertNoViolations(
            """
            // Lorem ipsum
            """.trimIndent(),
        )
        assertNoViolations(
            """
            /** Lorem ipsum */
            """.trimIndent(),
        )
    }
}
