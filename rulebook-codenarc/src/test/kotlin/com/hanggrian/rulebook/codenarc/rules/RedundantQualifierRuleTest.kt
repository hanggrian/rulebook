package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.assertProperties
import com.hanggrian.rulebook.codenarc.violationOf
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertIs

class RedundantQualifierRuleTest : AbstractRuleTestCase<RedundantQualifierRule>() {
    override fun createRule() = RedundantQualifierRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()

        assertIs<RedundantQualifierRule.Visitor>(rule.astVisitor)
    }

    @Test
    fun `Consistent class qualifiers`() =
        assertNoViolations(
            """
            import java.lang.String

            class QualifierConsistency {
                String property = new String()

                def parameter(String param) {}

                def call() {
                    String.format('%s', 'Hello World')
                }
            }
            """.trimIndent(),
        )

    @Test
    fun `Redundant class qualifiers`() =
        assertViolations(
            """
            import java.lang.String

            class QualifierConsistency {
                java.lang.String property = new java.lang.String()

                def parameter(java.lang.String param) {}

                def call() {
                    java.lang.String.format('%s', 'Hello World')
                }
            }
            """.trimIndent(),
            violationOf(
                4,
                "java.lang.String property = new java.lang.String()",
                "Omit redundant 'java.lang.'.",
            ),
            violationOf(
                6,
                "def parameter(java.lang.String param) {}",
                "Omit redundant 'java.lang.'.",
            ),
            violationOf(
                9,
                "java.lang.String.format('%s', 'Hello World')",
                "Omit redundant 'java.lang.'.",
            ),
        )

    @Test
    fun `Consistent method qualifiers`() =
        assertNoViolations(
            """
            import java.lang.String
            import java.lang.String.format

            class QualifierConsistency {
                String property = String.format('%s', 'Hello World')

                def call() {
                    format('%s', 'Hello World')
                }
            }
            """.trimIndent(),
        )

    @Test
    fun `Redundant method qualifiers`(): Unit =
        assertTwoViolations(
            """
            import java.lang.String.format

            class QualifierConsistency {
                String property = java.lang.String.format('%s', 'Hello World')

                def call() {
                    java.lang.String.format('%s', 'Hello World')
                }
            }
            """.trimIndent(),
            4,
            "String property = java.lang.String.format('%s', 'Hello World')",
            "Omit redundant 'java.lang.String.'.",
            7,
            "java.lang.String.format('%s', 'Hello World')",
            "Omit redundant 'java.lang.String.'.",
        )
}
