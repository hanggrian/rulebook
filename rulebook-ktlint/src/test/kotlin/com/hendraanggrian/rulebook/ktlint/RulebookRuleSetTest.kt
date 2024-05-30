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
            BlockTagDescriptionPunctuationRule::class,
            BlockTagSeparatingRule::class,
            ClassNameAcronymCapitalizationRule::class,
            ClassNameBlacklistingRule::class,
            ConstructorPositionRule::class,
            EmptyBlockCommentLineJoiningRule::class,
            EmptyCodeBlockWrappingRule::class,
            EmptyCommentLineJoiningRule::class,
            ExceptionSubclassThrowingRule::class,
            FileInitialJoiningRule::class,
            FileSizeLimitationRule::class,
            FunctionSingleExpressionRule::class,
            GenericsNameWhitelistingRule::class,
            IfStatementFlatteningRule::class,
            KotlinApiPriorityRule::class,
            ObjectStructuralComparisonRule::class,
            QualifierConsistencyRule::class,
            StaticClassPositionRule::class,
            SwitchCaseJoiningRule::class,
            TodoCommentStylingRule::class,
            IdentifierNameBlacklistingRule::class,
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
