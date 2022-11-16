package com.hendraanggrian.lints.ktlint

import com.google.common.truth.Truth.assertThat
import com.hendraanggrian.lints.ktlint.kdoc.SummaryContinuationFirstWordRule
import com.hendraanggrian.lints.ktlint.kdoc.TagDescriptionSentenceRule
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class LintsRuleSetTest {

    @Test
    fun `Constructor parameters`() {
        val ruleSet = LintsRuleSet()
        assertFalse(ruleSet.id.isBlank())
        assertNotNull(ruleSet.about.maintainer)
        assertNotNull(ruleSet.about.description)
        assertTrue("Apache" in ruleSet.about.license!!)
        assertTrue("github.com" in ruleSet.about.repositoryUrl!!)
        assertTrue("github.com" in ruleSet.about.issueTrackerUrl!!)
    }

    @Test
    fun `List of rules`() {
        assertThat(
            LintsRuleSet()
                .getRuleProviders()
                .map { it.createNewRuleInstance().javaClass.kotlin }
        ).containsExactly(
            ExceptionAmbiguityRule::class,
            FunctionSpecifyReturnTypeRule::class,
            SummaryContinuationFirstWordRule::class,
            TagDescriptionSentenceRule::class
        )
    }
}
