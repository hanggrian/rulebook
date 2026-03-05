package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.RuleTest
import kotlin.test.Test

class DuplicateWhitespaceRuleTest : RuleTest<DuplicateWhitespaceRule>() {
    override fun createRule() = DuplicateWhitespaceRule()

    @Test
    fun `Rule properties`() = rule.assertProperties()

    @Test
    fun `Single space between token`() =
        assertNoViolations(
            """
            def foo(var bar, var baz) {
                var qux = 1 + 2;
            }
            """.trimIndent(),
        )

    @Test
    fun `Unindented block tag description`() =
        assertTwoViolations(
            """
            def foo(var bar,  var baz) {
                var qux = 1 +${'\t'} 2;
            }
            """.trimIndent(),
            1,
            "def foo(var bar,  var baz) {",
            "Remove consecutive whitespace.",
            2,
            "var qux = 1 +\t 2;",
            "Remove consecutive whitespace.",
        )

    @Test
    fun `Skip block comment asterisk`() =
        assertNoViolations(
            """
            /**
             * @return the new size
             *     of the group.
             */
            def foo() {}
            """.trimIndent(),
        )
}
