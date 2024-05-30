package com.hendraanggrian.rulebook.codenarc

import com.hendraanggrian.rulebook.codenarc.ClassNameAcronymCapitalizationRule.Companion.MSG
import com.hendraanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test

class ClassNameAcronymCapitalizationRuleTest :
    AbstractRuleTestCase<ClassNameAcronymCapitalizationRule>() {
    override fun createRule() = ClassNameAcronymCapitalizationRule()

    @Test
    fun `Rule properties`(): Unit = rule.assertProperties()

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
            violationOf(2, "interface MySQLInterface {}", Messages.get(MSG, "MySqlInterface")),
            violationOf(3, "@interface MySQLAnnotation {}", Messages.get(MSG, "MySqlAnnotation")),
            violationOf(4, "enum MySQLEnum {}", Messages.get(MSG, "MySqlEnum")),
        )
}
