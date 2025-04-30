package com.hanggrian.rulebook.codenarc

import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertIs

class ClassNameAbbreviationRuleTest : AbstractRuleTestCase<ClassNameAbbreviationRule>() {
    override fun createRule() = ClassNameAbbreviationRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()

        assertIs<ClassNameAbbreviationRule.Visitor>(rule.astVisitor)
    }

    @Test
    fun `Class names with lowercase abbreviation`() =
        assertNoViolations(
            """
            class MySqlClass {}

            interface MySqlInterface {}

            @interface MySqlAnnotation {}

            enum MySqlEnum {}
            """.trimIndent(),
        )

    @Test
    fun `Class names with upppercase abbreviation`() =
        assertViolations(
            """
            class MySQLClass {}

            interface MySQLInterface {}

            @interface MySQLAnnotation {}

            enum MySQLEnum {}
            """.trimIndent(),
            violationOf(1, "class MySQLClass {}", "Rename abbreviation to 'MySqlClass'."),
            violationOf(
                3,
                "interface MySQLInterface {}",
                "Rename abbreviation to 'MySqlInterface'.",
            ),
            violationOf(
                5,
                "@interface MySQLAnnotation {}",
                "Rename abbreviation to 'MySqlAnnotation'.",
            ),
            violationOf(7, "enum MySQLEnum {}", "Rename abbreviation to 'MySqlEnum'."),
        )
}
