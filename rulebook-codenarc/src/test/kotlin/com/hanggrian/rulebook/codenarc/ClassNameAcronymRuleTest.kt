package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.ClassNameAcronymRule.Companion.MSG
import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertIs

class ClassNameAcronymRuleTest : AbstractRuleTestCase<ClassNameAcronymRule>() {
    override fun createRule() = ClassNameAcronymRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()

        assertIs<ClassNameAcronymRule.Visitor>(rule.astVisitor)
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
            violationOf(1, "class MySQLClass {}", Messages.get(MSG, "MySqlClass")),
            violationOf(3, "interface MySQLInterface {}", Messages.get(MSG, "MySqlInterface")),
            violationOf(5, "@interface MySQLAnnotation {}", Messages.get(MSG, "MySqlAnnotation")),
            violationOf(7, "enum MySQLEnum {}", Messages.get(MSG, "MySqlEnum")),
        )
}
