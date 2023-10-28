package com.hendraanggrian.rulebook

import com.hendraanggrian.rulebook.codenarc.InvertIfConditionRule
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

class RulebookRuleTest {
    @Test
    fun `Unsupported operations`() {
        val rule = InvertIfConditionRule()
        assertThrows<UnsupportedOperationException> { rule.name = "AnotherName" }
        assertThrows<UnsupportedOperationException> { rule.priority = 1 }
    }
}
