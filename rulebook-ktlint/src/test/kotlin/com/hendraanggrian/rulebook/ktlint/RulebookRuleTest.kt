package com.hendraanggrian.rulebook.ktlint

import kotlin.test.Test
import kotlin.test.assertFalse

class RulebookRuleTest {
    @Test
    fun `Rule constants`() {
        val rule = InvertIfConditionRule()
        assertFalse(rule.ruleId.value.isBlank())
        assertFalse(rule.about.maintainer.isBlank())
        assertFalse(rule.about.repositoryUrl.isBlank())
        assertFalse(rule.about.issueTrackerUrl.isBlank())
    }
}
