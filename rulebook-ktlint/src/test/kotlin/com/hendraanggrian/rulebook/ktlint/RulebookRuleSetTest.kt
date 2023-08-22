package com.hendraanggrian.rulebook.ktlint

import com.google.common.truth.Truth.assertThat
import com.hendraanggrian.rulebook.ktlint.docs.TagDescriptionPunctuationRule
import com.hendraanggrian.rulebook.ktlint.docs.TagGroupAfterNewlineRule
import kotlin.test.Test
import kotlin.test.assertFalse

class RulebookRuleSetTest {
    @Test
    fun `Rule set setup`() {
        assertFalse(RULEBOOK_ID.value.isBlank())
        assertFalse(RULEBOOK_ABOUT.maintainer.isBlank())
        assertFalse(RULEBOOK_ABOUT.repositoryUrl.isBlank())
        assertFalse(RULEBOOK_ABOUT.issueTrackerUrl.isBlank())
    }

    @Test
    fun `List of rules`() {
        assertThat(
            RulebookRuleSet()
                .getRuleProviders()
                .map { it.createNewRuleInstance().javaClass.kotlin }
        ).containsExactly(
            TagDescriptionPunctuationRule::class,
            TagGroupAfterNewlineRule::class,
            ClassBodyStartingNewlineRule::class,
            FunctionReturnTypeRule::class,
            AllNameAcronymRule::class,
            SwitchEntryNoNewlineRule::class,
            ThrowAmbiguityRule::class,
            AllKotlinApiRule::class
        )
    }
}
