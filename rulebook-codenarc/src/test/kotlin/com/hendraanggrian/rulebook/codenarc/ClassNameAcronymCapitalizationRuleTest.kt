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
            mapOf(
                "line" to 1,
                "source" to "class MySQLClass {}",
                "message" to Messages.get(MSG, "MySqlClass"),
            ),
            mapOf(
                "line" to 2,
                "source" to "interface MySQLInterface {}",
                "message" to Messages.get(MSG, "MySqlInterface"),
            ),
            mapOf(
                "line" to 3,
                "source" to "@interface MySQLAnnotation {}",
                "message" to Messages.get(MSG, "MySqlAnnotation"),
            ),
            mapOf(
                "line" to 4,
                "source" to "enum MySQLEnum {}",
                "message" to Messages.get(MSG, "MySqlEnum"),
            ),
        )
}
