package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.rules.RulebookRule
import com.pinterest.ktlint.rule.engine.core.api.RuleProvider
import com.pinterest.ktlint.test.KtLintAssertThat
import kotlin.test.assertEquals
import kotlin.test.assertFalse

@Suppress("JavaDefaultMethodsNotOverriddenByDelegation")
object AllRules : Set<RuleProvider> by RulebookRuleSet().getRuleProviders()

open class RuleTest {
    fun KtLintAssertThat.asScript(name: String = "test.kts") = asFileWithPath(name)

    inline fun <reified T : RulebookRule> T.assertProperties() {
        assertEquals(
            "rulebook:" +
                T::class
                    .java
                    .simpleName
                    .substringBefore("Rule")
                    .replace(Regex("([a-z])([A-Z])"), "$1-$2")
                    .lowercase(),
            ruleId.value,
        )
        assertFalse(about.maintainer.isBlank())
        assertFalse(about.repositoryUrl.isBlank())
        assertFalse(about.issueTrackerUrl.isBlank())
    }
}
