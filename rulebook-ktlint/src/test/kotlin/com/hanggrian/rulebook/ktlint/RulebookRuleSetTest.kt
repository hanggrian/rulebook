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
            BlockTagOrderingRule::class,
            BlockTagPunctuationRule::class,
            BuiltinFunctionPositionRule::class,
            BuiltinTypePriorityRule::class,
            CaseLineJoiningRule::class,
            ClassFinalNameDisallowingRule::class,
            ClassMemberOrderingRule::class,
            ClassNameAcronymCapitalizationRule::class,
            CommentLineJoiningRule::class,
            CommentLineTrimmingRule::class,
            ContractFunctionInliningRule::class,
            DefaultFlatteningRule::class,
            ElseFlatteningRule::class,
            ElvisWrappingRule::class,
            EmptyCodeBlockUnwrappingRule::class,
            ExceptionExtendingRule::class,
            ExceptionThrowingRule::class,
            FileInitialLineTrimmingRule::class,
            FileSizeLimitationRule::class,
            FloatLiteralTaggingRule::class,
            GenericsNameAllowingRule::class,
            IfElseFlatteningRule::class,
            InfixCallWrappingRule::class,
            InnerClassPositionRule::class,
            NullStructuralEqualityRule::class,
            OverloadFunctionPositionRule::class,
            PropertyNameInteroperabilityRule::class,
            SwitchCaseBranchingRule::class,
            TodoCommentFormattingRule::class,
            VariableNameDisallowingRule::class,
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
