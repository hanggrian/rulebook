package com.hendraanggrian.rulebook.ktlint

import com.google.common.truth.Truth.assertThat
import com.hendraanggrian.rulebook.ktlint.docs.AddBlankLineInDocumentationRule
import com.hendraanggrian.rulebook.ktlint.docs.PunctuateDocumentationTagRule
import kotlin.test.Test

class RulebookRuleSetTest {
    @Test
    fun `List of rules`() {
        assertThat(
            RulebookRuleSet()
                .getRuleProviders()
                .map { it.createNewRuleInstance().javaClass.kotlin },
        ).containsExactly(
            AddBlankLineInDocumentationRule::class,
            PunctuateDocumentationTagRule::class,
            InvertIfConditionRule::class,
            RemoveBlankLineInSwitchRule::class,
            RenameAbbreviationWordRule::class,
            RenameUncommonGenericsRule::class,
            RenameMeaninglessWordRule::class,
            ReplaceWithKotlinApiRule::class,
            ReplaceWithStructuralEqualityRule::class,
            SpecifyTypeExplicitlyRule::class,
            ThrowExceptionSubclassRule::class,
        )
    }
}
