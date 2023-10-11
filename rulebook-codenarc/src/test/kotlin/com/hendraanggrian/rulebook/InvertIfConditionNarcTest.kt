package com.hendraanggrian.rulebook

import com.hendraanggrian.rulebook.codenarc.InvertIfConditionNarc
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertEquals

class InvertIfConditionNarcTest : AbstractRuleTestCase<InvertIfConditionNarc>() {
    override fun createRule(): InvertIfConditionNarc = InvertIfConditionNarc()

    @Test
    fun `Rule properties`() {
        assertEquals(3, rule.priority)
        assertEquals("InvertIfCondition", rule.name)
    }

    @Test
    fun `Correct format`() = assertNoViolations(
        """
        void foo() {
            if (true) {
                return
            }
            println()
        }
        """.trimIndent()
    )

    @Test
    fun `Only 1 line in if statement`() = assertNoViolations(
        """
        void foo() {
            if (true) {
                println()
            }
        }
        """.trimIndent()
    )

    @Test
    fun `At least 2 lines in if statement`() = assertSingleViolation(
        """
        void foo() {
            if (true) {
                println()
                println()
            }
        }
        """.trimIndent(),
        2
    )

    @Test
    fun `If statement with else`() = assertNoViolations(
        """
        void foo() {
            if (true) {
                println()
                println()
            } else if (false) {
                println()
                println()
            } else {
                println()
                println()
            }
        }
        """.trimIndent()
    )
}
