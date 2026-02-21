package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.RuleTest
import kotlin.test.Test

class BracketsClipRuleTest : RuleTest<BracketsClipRule>() {
    override fun createRule() = BracketsClipRule()

    @Test
    fun `Rule properties`() = rule.assertProperties()

    @Test
    fun `Wrapped brackets`() =
        assertNoViolations(
            """
            def foo() {
                var bar = []
                var baz = [:]
            }
            """.trimIndent(),
        )

    @Test
    fun `Unwrapped brackets`() =
        assertTwoViolations(
            """
            def foo() {
                var bar = [

                ]
                var baz = [
                    :
                ]
            }
            """.trimIndent(),
            2,
            "var bar = [",
            "Convert into '[]'.",
            5,
            "var baz = [",
            "Convert into '[]'.",
        )

    @Test
    fun `Allow brackets with comment`() =
        assertNoViolations(
            """
            def foo() {
                var bar = [
                    // Lorem ipsum.
                ]
                var baz = [
                    // Lorem ipsum.
                    :
                    // Lorem ipsum.
                ]
            }
            """.trimIndent(),
        )
}
