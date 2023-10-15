package com.hendraanggrian.rulebook.ktlint

import com.google.common.truth.Truth.assertThat
import com.hendraanggrian.rulebook.ktlint.docs.AddBlankLineInDocumentationRule
import com.hendraanggrian.rulebook.ktlint.docs.PunctuateDocumentationTagRule
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
            AddBlankLineInDocumentationRule::class,
            PunctuateDocumentationTagRule::class,
            AddBlankLineInClassRule::class,
            InvertIfConditionRule::class,
            RemoveBlankLineInSwitchRule::class,
            RenameAbbreviationInIdentifierRule::class,
            RenameGenericsRule::class,
            RenameUnderscoreInIdentifierRule::class,
            ReplaceWithAbstractTypeRule::class,
            ReplaceWithKotlinApiRule::class,
            SpecifyTypeExplicitlyRule::class,
            ThrowExceptionSubclassRule::class
        )
    }
}
