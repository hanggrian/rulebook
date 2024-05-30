package com.hendraanggrian.rulebook.codenarc

import com.hendraanggrian.rulebook.codenarc.QualifierConsistencyRule.Companion.MSG
import com.hendraanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test

class QualifierConsistencyRuleTest : AbstractRuleTestCase<QualifierConsistencyRule>() {
    override fun createRule() = QualifierConsistencyRule()

    @Test
    fun `Rule properties`(): Unit = rule.assertProperties()

    @Test
    fun `Consistent qualifiers`() =
        assertNoViolations(
            """
            import java.lang.String

            class QualifierConsistency {
                String property = new String()
                void parameter(String param) {}
                void call() {
                    String.format("%s", "Hello World")
                }
            }
            """.trimIndent(),
        )

    @Test
    fun `Redundant qualifiers`() =
        assertViolations(
            """
            import java.lang.String

            class QualifierConsistency {
                java.lang.String property = new java.lang.String()
                void parameter(java.lang.String param) {}
                void call() {
                    java.lang.String.format("%s", "Hello World")
                }
            }
            """.trimIndent(),
            violationOf(4, "java.lang.String property = new java.lang.String()", Messages[MSG]),
            violationOf(5, "void parameter(java.lang.String param) {}", Messages[MSG]),
            violationOf(7, "java.lang.String.format(\"%s\", \"Hello World\")", Messages[MSG]),
        )
}
