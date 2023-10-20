package com.hendraanggrian.rulebook.internals

import com.hendraanggrian.rulebook.codenarc.InvertIfConditionNarc
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

class RulebookNarcTest {
    @Test
    fun `Unsupported operations`() {
        val rule = InvertIfConditionNarc()
        assertThrows<UnsupportedOperationException> { rule.name = "AnotherName" }
        assertThrows<UnsupportedOperationException> { rule.priority = 1 }
    }
}
