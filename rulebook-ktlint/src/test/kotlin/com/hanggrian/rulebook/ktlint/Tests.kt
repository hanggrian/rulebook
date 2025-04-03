package com.hanggrian.rulebook.ktlint

import com.pinterest.ktlint.rule.engine.core.api.RuleProvider
import kotlin.test.assertEquals
import kotlin.test.assertFalse

object AllRules : Set<RuleProvider> by RulebookRuleSet().getRuleProviders()

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
