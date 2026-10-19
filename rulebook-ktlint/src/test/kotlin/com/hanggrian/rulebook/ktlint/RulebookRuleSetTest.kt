package com.hanggrian.rulebook.ktlint

import com.google.common.truth.Truth.assertThat
import com.hanggrian.rulebook.ktlint.rules.AbbreviationAsWordRule
import com.hanggrian.rulebook.ktlint.rules.AbstractFunctionInAbstractClassRule
import com.hanggrian.rulebook.ktlint.rules.BlankLineBeforeBlockTagsRule
import com.hanggrian.rulebook.ktlint.rules.BlockCommentClippingRule
import com.hanggrian.rulebook.ktlint.rules.BlockCommentSpacingRule
import com.hanggrian.rulebook.ktlint.rules.BlockCommentTrimmingRule
import com.hanggrian.rulebook.ktlint.rules.BlockTagIndentationRule
import com.hanggrian.rulebook.ktlint.rules.BlockTagOrderRule
import com.hanggrian.rulebook.ktlint.rules.BlockTagPunctuationRule
import com.hanggrian.rulebook.ktlint.rules.BooleanPropertyInteroperabilityRule
import com.hanggrian.rulebook.ktlint.rules.BracesClippingRule
import com.hanggrian.rulebook.ktlint.rules.CentralizedDependencyRule
import com.hanggrian.rulebook.ktlint.rules.CommentTrimmingRule
import com.hanggrian.rulebook.ktlint.rules.CommonFunctionPositionRule
import com.hanggrian.rulebook.ktlint.rules.ElvisWrappingRule
import com.hanggrian.rulebook.ktlint.rules.ExceptionInheritanceRule
import com.hanggrian.rulebook.ktlint.rules.GenericNameRule
import com.hanggrian.rulebook.ktlint.rules.IllegalVariableNameRule
import com.hanggrian.rulebook.ktlint.rules.InfixCallWrappingRule
import com.hanggrian.rulebook.ktlint.rules.InlinedContractFunctionRule
import com.hanggrian.rulebook.ktlint.rules.InnerClassPositionRule
import com.hanggrian.rulebook.ktlint.rules.LazyApiRule
import com.hanggrian.rulebook.ktlint.rules.LiteralComparisonRule
import com.hanggrian.rulebook.ktlint.rules.LowercaseFRule
import com.hanggrian.rulebook.ktlint.rules.LowercaseHexRule
import com.hanggrian.rulebook.ktlint.rules.MaxFileSizeRule
import com.hanggrian.rulebook.ktlint.rules.MemberOrderRule
import com.hanggrian.rulebook.ktlint.rules.NoBroadThrowRule
import com.hanggrian.rulebook.ktlint.rules.NoConfusingAssertionRule
import com.hanggrian.rulebook.ktlint.rules.NoConfusingPredicateRule
import com.hanggrian.rulebook.ktlint.rules.NoDeprecatedAnnotationRule
import com.hanggrian.rulebook.ktlint.rules.NoDeprecatedTypeRule
import com.hanggrian.rulebook.ktlint.rules.NoDuplicateBlankLineInBlockCommentRule
import com.hanggrian.rulebook.ktlint.rules.NoDuplicateBlankLineInCommentRule
import com.hanggrian.rulebook.ktlint.rules.NoLeadingBlankLineRule
import com.hanggrian.rulebook.ktlint.rules.NoLonelyCaseRule
import com.hanggrian.rulebook.ktlint.rules.NoLonelyConfigurationRule
import com.hanggrian.rulebook.ktlint.rules.NoLonelyIfRule
import com.hanggrian.rulebook.ktlint.rules.NoMeaninglessWordRule
import com.hanggrian.rulebook.ktlint.rules.NoNestedIfElseRule
import com.hanggrian.rulebook.ktlint.rules.NoRedundantDefaultRule
import com.hanggrian.rulebook.ktlint.rules.NoRedundantElseRule
import com.hanggrian.rulebook.ktlint.rules.NoRedundantIfRule
import com.hanggrian.rulebook.ktlint.rules.NoUselessContinueRule
import com.hanggrian.rulebook.ktlint.rules.NoUselessReturnRule
import com.hanggrian.rulebook.ktlint.rules.OverloadFunctionPositionRule
import com.hanggrian.rulebook.ktlint.rules.RootProjectNameRule
import com.hanggrian.rulebook.ktlint.rules.ScriptFileNameRule
import com.hanggrian.rulebook.ktlint.rules.ShorthandAssignmentRule
import com.hanggrian.rulebook.ktlint.rules.SimpleBooleanExpressionRule
import com.hanggrian.rulebook.ktlint.rules.SimpleSizeComparisonRule
import com.hanggrian.rulebook.ktlint.rules.SpecificAssertionRule
import com.hanggrian.rulebook.ktlint.rules.TodoCommentStyleRule
import com.pinterest.ktlint.rule.engine.core.api.RuleProvider
import com.pinterest.ktlint.ruleset.standard.StandardRuleSetProvider
import kotlin.test.Test

class RulebookRuleSetTest {
    private val rules by lazy { RulebookRuleSet().getRuleProviders() }

    @Test
    fun `All rules`() {
        assertThat(rules.map { it.createNewRuleInstance().javaClass.kotlin })
            .containsExactly(
                AbbreviationAsWordRule::class,
                AbstractFunctionInAbstractClassRule::class,
                BlockCommentClippingRule::class,
                BlockCommentSpacingRule::class,
                BlockCommentTrimmingRule::class,
                BlockTagIndentationRule::class,
                BlockTagOrderRule::class,
                BlockTagPunctuationRule::class,
                BooleanPropertyInteroperabilityRule::class,
                BracesClippingRule::class,
                CentralizedDependencyRule::class,
                CommonFunctionPositionRule::class,
                CommentTrimmingRule::class,
                ElvisWrappingRule::class,
                ExceptionInheritanceRule::class,
                GenericNameRule::class,
                NoBroadThrowRule::class,
                IllegalVariableNameRule::class,
                InfixCallWrappingRule::class,
                InlinedContractFunctionRule::class,
                InnerClassPositionRule::class,
                LazyApiRule::class,
                LiteralComparisonRule::class,
                LowercaseFRule::class,
                LowercaseHexRule::class,
                MaxFileSizeRule::class,
                MemberOrderRule::class,
                NoMeaninglessWordRule::class,
                BlankLineBeforeBlockTagsRule::class,
                NoNestedIfElseRule::class,
                NoConfusingAssertionRule::class,
                NoConfusingPredicateRule::class,
                NoDeprecatedAnnotationRule::class,
                NoDeprecatedTypeRule::class,
                NoDuplicateBlankLineInBlockCommentRule::class,
                NoDuplicateBlankLineInCommentRule::class,
                NoLeadingBlankLineRule::class,
                NoLonelyCaseRule::class,
                NoLonelyConfigurationRule::class,
                NoLonelyIfRule::class,
                NoUselessContinueRule::class,
                NoUselessReturnRule::class,
                NoRedundantDefaultRule::class,
                NoRedundantElseRule::class,
                NoRedundantIfRule::class,
                OverloadFunctionPositionRule::class,
                RootProjectNameRule::class,
                ShorthandAssignmentRule::class,
                SimpleBooleanExpressionRule::class,
                SimpleSizeComparisonRule::class,
                SpecificAssertionRule::class,
                ScriptFileNameRule::class,
                TodoCommentStyleRule::class,
            )
    }

    @Test
    fun `No overlapping ID`() {
        assertThat(rules.ids.distinct().size)
            .isEqualTo(rules.ids.size)
        assertThat(rules.ids)
            .containsNoneIn(StandardRuleSetProvider().getRuleProviders().ids)
    }

    private val Set<RuleProvider>.ids
        get() =
            map {
                it
                    .createNewRuleInstance()
                    .ruleId
                    .value
                    .substringAfterLast(':')
            }
}
