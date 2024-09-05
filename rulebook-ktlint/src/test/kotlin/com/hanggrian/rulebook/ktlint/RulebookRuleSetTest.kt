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
            CaseLineJoiningRule::class,
            ClassFinalNameDisallowingRule::class,
            ClassMemberOrderingRule::class,
            ClassNameAcronymCapitalizationRule::class,
            CommentLineJoiningRule::class,
            CommentLineTrimmingRule::class,
            DefaultDenestingRule::class,
            ElseDenestingRule::class,
            ElvisWrappingRule::class,
            EmptyCodeBlockUnwrappingRule::class,
            ExceptionExtendingRule::class,
            ExceptionSubclassThrowingRule::class,
            FileInitialLineTrimmingRule::class,
            FileSizeLimitationRule::class,
            GenericsNameAllowingRule::class,
            IdentifierNameDisallowingRule::class,
            IfElseDenestingRule::class,
            InfixCallWrappingRule::class,
            InnerClassPositionRule::class,
            KotlinPropertyNameInteroperabilityRule::class,
            KotlinTypePriorityRule::class,
            NullStructuralEqualityRule::class,
            OverloadFunctionPositionRule::class,
            SpecialFunctionPositionRule::class,
            SwitchCaseBranchingRule::class,
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
