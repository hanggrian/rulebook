package com.hanggrian.rulebook.ktlint

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
            BlockCommentLineJoiningRule::class,
            BlockCommentLineTrimmingRule::class,
            BlockCommentSpacingRule::class,
            BlockTagInitialLineSpacingRule::class,
            BlockTagPunctuationRule::class,
            CaseLineJoiningRule::class,
            ClassFinalNameBlacklistingRule::class,
            ClassNameAcronymCapitalizationRule::class,
            ClassOrganizationRule::class,
            CommentLineJoiningRule::class,
            CommentLineTrimmingRule::class,
            DefaultFlatteningRule::class,
            ElseFlatteningRule::class,
            ElvisWrappingRule::class,
            EmptyCodeBlockConcisenessRule::class,
            ExceptionExtendingRule::class,
            ExceptionSubclassThrowingRule::class,
            FileInitialLineTrimmingRule::class,
            FileSizeLimitationRule::class,
            GenericsNameWhitelistingRule::class,
            IdentifierNameBlacklistingRule::class,
            IfElseFlatteningRule::class,
            InnerClassPositionRule::class,
            KotlinApiPriorityRule::class,
            NullStructuralEqualityRule::class,
            OverloadFunctionPositionRule::class,
            PropertyNameJavaInteroperabilityRule::class,
            QualifierConsistencyRule::class,
            SpecialFunctionPositionRule::class,
            SwitchMultipleBranchingRule::class,
            TodoCommentStylingRule::class,
        )
    }

    @Test
    fun `No overlapping ID`() {
        assertThat(RulebookRuleSet().getRuleProviders().ids)
            .containsNoneIn(StandardRuleSetProvider().getRuleProviders().ids)
    }

    private val Set<RuleProvider>.ids
        get() =
            map {
                it
                    .createNewRuleInstance()
                    .ruleId.value
                    .substringAfterLast(':')
            }
}
