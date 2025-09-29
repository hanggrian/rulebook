package com.hanggrian.rulebook.codenarc

import com.google.common.truth.Truth.assertThat
import org.codenarc.rule.Rule
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import kotlin.test.Test

@ExtendWith(MockitoExtension::class)
class RulesTest {
    @Mock
    private lateinit var rule: Rule

    @Test
    fun createViolation() {
        val violation = rule.createViolation(1, "int i = 0", "i cannot be zero")
        assertThat(violation.rule).isEqualTo(rule)
        assertThat(violation.lineNumber).isEqualTo(1)
        assertThat(violation.sourceLine).isEqualTo("int i = 0")
        assertThat(violation.message).isEqualTo("i cannot be zero")
    }
}
