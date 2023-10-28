package com.hendraanggrian.rulebook.ktlint

import com.google.common.truth.Truth.assertThat
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
            InvertIfConditionRule::class,
            PunctuateDocumentationTagRule::class,
            RemoveBlankLineInSwitchRule::class,
            RemoveMeaninglessWordRule::class,
            RenameAbbreviationWordRule::class,
            RenameUncommonGenericsRule::class,
            ReplaceWithKotlinApiRule::class,
            ReplaceWithStructuralEqualityRule::class,
            SpecifyTypeExplicitlyRule::class,
            ThrowExceptionSubclassRule::class,
        )
    }
}
