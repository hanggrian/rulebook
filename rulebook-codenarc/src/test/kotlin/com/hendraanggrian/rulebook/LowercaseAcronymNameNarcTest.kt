package com.hendraanggrian.rulebook

import com.hendraanggrian.rulebook.codenarc.LowercaseAcronymNameNarc
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertEquals

class LowercaseAcronymNameNarcTest : AbstractRuleTestCase<LowercaseAcronymNameNarc>() {
    override fun createRule(): LowercaseAcronymNameNarc = LowercaseAcronymNameNarc()

    @Test
    fun `Rule properties`() {
        assertEquals(3, rule.priority)
        assertEquals("LowercaseAcronymName", rule.name)
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
