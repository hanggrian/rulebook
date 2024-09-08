package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.KotlinContractFunctionInliningRule.Companion.MSG
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class KotlinContractFunctionInliningRuleTest {
    private val assertThatCode = assertThatRule { KotlinContractFunctionInliningRule() }

    @Test
    fun `Rule properties`() = KotlinContractFunctionInliningRule().assertProperties()

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
        ).hasLintViolationWithoutAutoCorrect(1, 1, Messages[MSG])
}
