package com.hanggrian.rulebook.ktlint

import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class KtlintTest : AllRulesTest() {
    @Test
    fun com_pinterest_ktlint_rule_engine_internal_RuleExecutionContext() {
        assertThatCodeAll(getCode("ktlint/RuleExecutionContext.kt"))
            .hasLintViolationsForAdditionalRule(
                LintViolation(96, 9, "Invert 'if' condition.", false),
                LintViolation(113, 19, "Omit redundant 'else' condition.", false),
                LintViolation(239, 25, "Omit separator ':'.", false),
                LintViolation(241, 25, "Omit separator ':'.", false),
                LintViolation(287, 5, "Invert 'if' condition.", false),
                LintViolation(300, 5, "Extend from class 'Exception'.", false),
            )
    }
}
