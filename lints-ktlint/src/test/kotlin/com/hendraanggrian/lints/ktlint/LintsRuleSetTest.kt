package com.hendraanggrian.lints.ktlint

import com.google.common.truth.Truth.assertThat
import com.hendraanggrian.lints.ktlint.kdoc.SummaryContinuationRule
import com.hendraanggrian.lints.ktlint.kdoc.TagDescriptionSentenceRule
import com.hendraanggrian.lints.ktlint.kdoc.TagsStartingEmptyLineRule
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
        assertTrue("github.com" in ruleSet.about.license!!)
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
            SummaryContinuationRule::class,
            TagDescriptionSentenceRule::class,
            TagsStartingEmptyLineRule::class,
            ExceptionAmbiguityRule::class,
            FilenameAcronymRule::class,
            FunctionReturnTypeRule::class,
            TypeKotlinApiRule::class
        )
    }
}
