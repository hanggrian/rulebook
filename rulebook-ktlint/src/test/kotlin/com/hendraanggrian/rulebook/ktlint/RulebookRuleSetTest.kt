package com.hendraanggrian.rulebook.ktlint

import com.google.common.truth.Truth.assertThat
import com.hendraanggrian.rulebook.ktlint.docs.AddEmptyLineBeforeTagsRule
import com.hendraanggrian.rulebook.ktlint.docs.PunctuateTagRule
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
            AddEmptyLineBeforeTagsRule::class,
            PunctuateTagRule::class,
            AddEmptyLineInClassRule::class,
            DescribeThrowRule::class,
            InvertIfConditionRule::class,
            LowercaseAcronymNameRule::class,
            NoUnderscoreNameRule::class,
            RemoveEmptyLineInSwitchRule::class,
            SpecifyReturnTypeRule::class,
            StandardizeStringLiteralRule::class,
            UseAbstractCollectionRule::class,
            UseKotlinApiRule::class
        )
    }
}