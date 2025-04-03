package com.hanggrian.rulebook.ktlint

import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class ContractFunctionDefinitionRuleTest {
    private val assertThatCode = assertThatRule { ContractFunctionDefinitionRule() }

    @Test
    fun `Rule properties`() = ContractFunctionDefinitionRule().assertProperties()

    @Test
    fun `Contract function with inline`() =
        assertThatCode(
            """
            inline fun foo(block: () -> Unit) {
                contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
                block()
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Contract function without inline`() =
        assertThatCode(
            """
            fun foo(block: () -> Unit) {
                contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
                block()
            }
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(1, 1, "Put 'inline' modifier.")

    @Test
    fun `Skip function without callsInPlace`() =
        assertThatCode(
            """
            fun foo() {
                contract { returns }
            }
            """.trimIndent(),
        ).hasNoLintViolations()
}
