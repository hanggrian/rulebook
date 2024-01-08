package com.hendraanggrian.rulebook.ktlint

import com.google.common.truth.Truth.assertThat
import com.pinterest.ktlint.rule.engine.core.api.RuleProvider
import com.pinterest.ktlint.ruleset.standard.StandardRuleSetProvider
import kotlin.test.Test

class RulebookRuleSetTest {
    @Test
    fun `All rules`() {
        assertThat(
            RulebookRuleSet()
                .getRuleProviders()
                .map { it.createNewRuleInstance().javaClass.kotlin },
        ).containsExactly(
            BlockCommentSpacingRule::class,
            BlockTagPunctuationRule::class,
            BlockTagsInitialSpacingRule::class,
            ConstructorPositionRule::class,
            EmptyBlockWrappingRule::class,
            ExceptionThrowingRule::class,
            FileInitialWrappingRule::class,
            FileSizeLimitationRule::class,
            FunctionExpressionRule::class,
            GenericsNamingRule::class,
            IfStatementNestingRule::class,
            KotlinApiConsistencyRule::class,
            ObjectsComparisonRule::class,
            PropertyIdiomaticNamingRule::class,
            QualifierRedundancyRule::class,
            SourceAcronymCapitalizationRule::class,
            SourceWordMeaningRule::class,
            StaticClassPositionRule::class,
            StringInterpolationRule::class,
            SwitchStatementWrappingRule::class,
            TodoCommentFormattingRule::class,
        )
    }

    @Test
    fun `No overlapping ID`() {
        val standardIds = StandardRuleSetProvider().getRuleProviders().ids
        RulebookRuleSet().getRuleProviders().ids
            .forEach { assertThat(it).isNotIn(standardIds) }
    }

    private val Set<RuleProvider>.ids
        get() = map { it.createNewRuleInstance().ruleId.value.substringAfterLast(':') }
}
