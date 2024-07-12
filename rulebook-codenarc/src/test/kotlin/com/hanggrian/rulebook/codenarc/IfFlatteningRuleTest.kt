package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.IfFlatteningRule.Companion.MSG
import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test

class IfFlatteningRuleTest : AbstractRuleTestCase<IfFlatteningRule>() {
    override fun createRule() = IfFlatteningRule()

    @Test
    fun `Rule properties`() = rule.assertProperties()

    @Test
    fun `Empty or one statement in if statement`() =
        assertNoViolations(
            """
            void foo() {
                if (true) {
                }
            }

            void bar() {
                if (true) {
                    baz()
                }
            }
            """.trimIndent(),
        )

    @Test
    fun `Invert if with two statements`() =
        assertSingleViolation(
            """
            void foo() {
                if (true) {
                    bar()
                    baz()
                }
            }
            """.trimIndent(),
            2,
            "if (true) {",
            Messages[MSG],
        )

    @Test
    fun `Do not invert when there is else`() =
        assertNoViolations(
            """
            void foo() {
                if (true) {
                    baz()
                    baz()
                } else {
                    baz()
                }
            }
            """.trimIndent(),
        )
}
