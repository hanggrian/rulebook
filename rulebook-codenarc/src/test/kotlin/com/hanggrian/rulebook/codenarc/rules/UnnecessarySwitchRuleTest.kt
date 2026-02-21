package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.RuleTest
import kotlin.test.Test
import kotlin.test.assertIs

class UnnecessarySwitchRuleTest : RuleTest<UnnecessarySwitchRule>() {
    override fun createRule() = UnnecessarySwitchRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()
        assertIs<UnnecessarySwitchVisitor>(rule.astVisitor)
    }

    @Test
    fun `Multiple switch branches`() =
        assertNoViolations(
            """
            def foo() {
                switch (bar) {
                    case 0:
                        baz()
                        break
                    case 1:
                        baz()
                        break
                }
            }
            """.trimIndent(),
        )

    @Test
    fun `Single switch branch`() =
        assertSingleViolation(
            """
            def foo() {
                switch (bar) {
                    case 0:
                        baz()
                        break
                }
            }
            """.trimIndent(),
            2,
            "switch (bar) {",
            "Replace 'switch' with 'if' condition.",
        )

    @Test
    fun `Skip single branch if it has fall through condition`() =
        assertNoViolations(
            """
            def foo() {
                switch (bar) {
                    case 0:
                    case 1:
                        baz()
                        break
                }
            }
            """.trimIndent(),
        )
}
