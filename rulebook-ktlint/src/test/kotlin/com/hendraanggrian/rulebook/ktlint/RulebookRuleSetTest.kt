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
            AddBlankLineBeforeTagsRule::class,
            AvoidMeaninglessWordRule::class,
            CapitalizeFirstAcronymLetterRule::class,
            EndBlockTagWithPeriodRule::class,
            InvertIfConditionRule::class,
            RemoveBlankLineBetweenCasesRule::class,
            RemoveRedundantQualifierRule::class,
            ThrowNarrowerExceptionRule::class,
            UseCommonGenericsRule::class,
            UseExpressionFunctionRule::class,
            UseKotlinApiRule::class,
            UseStringTemplatesRule::class,
            UseStructuralEqualityRule::class,
        )
    }
}
