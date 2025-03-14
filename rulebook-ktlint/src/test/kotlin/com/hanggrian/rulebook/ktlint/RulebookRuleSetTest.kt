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
            AbstractClassFunctionAbstractionRule::class,
            BlockCommentLineJoiningRule::class,
            BlockCommentLineTrimmingRule::class,
            BlockCommentSignatureRule::class,
            BlockCommentSpacingRule::class,
            BlockTagIndentationRule::class,
            BlockTagInitialLineSpacingRule::class,
            BlockTagOrderingRule::class,
            BlockTagPunctuationRule::class,
            BuiltinFunctionPositionRule::class,
            BuiltinTypePriorityRule::class,
            CaseLineSpacingRule::class,
            ClassExceptionExtendingRule::class,
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
            ExceptionSubclassThrowingRule::class,
            FileInitialLineTrimmingRule::class,
            FileSizeLimitationRule::class,
            FloatSuffixLowercasingRule::class,
            GenericsNameAllowingRule::class,
            IfElseFlatteningRule::class,
            InfixCallWrappingRule::class,
            InnerClassPositionRule::class,
            NullStructuralEqualityRule::class,
            OverloadFunctionPositionRule::class,
            PredicateCallPositivityRule::class,
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
