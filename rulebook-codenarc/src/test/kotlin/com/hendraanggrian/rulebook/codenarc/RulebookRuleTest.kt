package com.hendraanggrian.rulebook.codenarc

import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

class RulebookRuleTest {
    @Test
    fun `Unsupported operations`() {
        val rule = IfStatementNestingRule()
        assertThrows<UnsupportedOperationException> { rule.name = "AnotherName" }
        assertThrows<UnsupportedOperationException> { rule.priority = 1 }
    }
}
