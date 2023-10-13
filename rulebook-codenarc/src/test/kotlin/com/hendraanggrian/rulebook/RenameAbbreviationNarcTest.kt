package com.hendraanggrian.rulebook

import com.hendraanggrian.rulebook.codenarc.RenameAbbreviationNarc
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertEquals

class RenameAbbreviationNarcTest : AbstractRuleTestCase<RenameAbbreviationNarc>() {
    override fun createRule(): RenameAbbreviationNarc = RenameAbbreviationNarc()

    @Test
    fun `Rule properties`() {
        assertEquals(3, rule.priority)
        assertEquals("RenameAbbreviation", rule.name)
    }

    @Test
    fun `Property acronym`() = assertSingleViolation(
        """
        class MyClass {
          String userJSON
        }
        """.trimIndent(),
        2
    )

    @Test
    fun `Function acronym`() = assertSingleViolation(
        """
        class MyClass {
          void blendARGB() {}
        }
        """.trimIndent(),
        2
    )
}
