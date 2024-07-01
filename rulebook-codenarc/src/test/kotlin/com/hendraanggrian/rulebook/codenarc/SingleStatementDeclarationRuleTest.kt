package com.hendraanggrian.rulebook.codenarc

import com.hendraanggrian.rulebook.codenarc.SingleStatementDeclarationRule.Companion.MSG
import com.hendraanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test

class SingleStatementDeclarationRuleTest : AbstractRuleTestCase<SingleStatementDeclarationRule>() {
    override fun createRule() = SingleStatementDeclarationRule()

    @Test
    fun `Rule properties`(): Unit = rule.assertProperties()

    @Test
    fun `Single statement`() =
        assertNoViolations(
            """
            void foo() {
                int bar = 1
                int baz = 2
            }
            """.trimIndent(),
        )

    @Test
    fun `Joined statements`() =
        assertTwoViolations(
            """
            void foo() {
                int bar = 1; int baz = 2
            }
            """.trimIndent(),
            2,
            "int bar = 1; int baz = 2",
            Messages[MSG],
            2,
            "int bar = 1; int baz = 2",
            Messages[MSG],
        )
}
