package com.hendraanggrian.rulebook.ktlint

import com.google.common.truth.Truth.assertThat
import com.hendraanggrian.rulebook.ktlint.kdoc.SummaryContinuationRule
import com.hendraanggrian.rulebook.ktlint.kdoc.TagDescriptionSentenceRule
import com.hendraanggrian.rulebook.ktlint.kdoc.TagsStartingWhitespaceRule
import kotlin.test.Test
import kotlin.test.assertFalse

class RulebookRuleSetTest {

    @Test
    fun `Constructor parameters`() {
        val ruleSet = RulebookRuleSet()
        assertFalse(ruleSet.id.isBlank())
        assertFalse(ruleSet.about.maintainer!!.isBlank())
        assertFalse(ruleSet.about.description!!.isBlank())
        assertFalse(ruleSet.about.license!!.isBlank())
        assertFalse(ruleSet.about.repositoryUrl!!.isBlank())
        assertFalse(ruleSet.about.issueTrackerUrl!!.isBlank())
    }

    @Test
    fun `List of rules`() {
        assertThat(
            RulebookRuleSet()
                .getRuleProviders()
                .map { it.createNewRuleInstance().javaClass.kotlin }
        ).containsExactly(
            SummaryContinuationRule::class,
            TagDescriptionSentenceRule::class,
            TagsStartingWhitespaceRule::class,
            ClassBodyStartingWhitespaceRule::class,
            ThrowExceptionAmbiguityRule::class,
            FunctionsReturnTypeRule::class,
            AllNameAcronymRule::class,
            TypesKotlinApiRule::class
        )
    }
}
