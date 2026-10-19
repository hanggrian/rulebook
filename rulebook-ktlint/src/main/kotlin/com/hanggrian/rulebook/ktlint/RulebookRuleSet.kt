package com.hanggrian.rulebook.ktlint

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
import com.pinterest.ktlint.cli.ruleset.core.api.RuleSetProviderV3
import com.pinterest.ktlint.rule.engine.core.api.RuleProvider
import com.pinterest.ktlint.rule.engine.core.api.RuleSetId

public class RulebookRuleSet : RuleSetProviderV3(ID) {
    override fun getRuleProviders(): Set<RuleProvider> =
        setOf(
            RuleProvider { BlockTagPunctuationRule() },
            RuleProvider { TodoCommentStyleRule() },
            // Clipping
            RuleProvider { BlockCommentClippingRule() },
            RuleProvider { BracesClippingRule() },
            // Declaring
            RuleProvider { AbstractFunctionInAbstractClassRule() },
            RuleProvider { ExceptionInheritanceRule() },
            RuleProvider { InlinedContractFunctionRule() },
            RuleProvider { LowercaseFRule() },
            RuleProvider { LowercaseHexRule() },
            RuleProvider { NoDeprecatedTypeRule() },
            // Expressing
            RuleProvider { LiteralComparisonRule() },
            RuleProvider { NoConfusingPredicateRule() },
            RuleProvider { SimpleBooleanExpressionRule() },
            RuleProvider { SimpleSizeComparisonRule() },
            // Formatting
            RuleProvider { MaxFileSizeRule() },
            // Naming
            RuleProvider { AbbreviationAsWordRule() },
            RuleProvider { NoMeaninglessWordRule() },
            RuleProvider { IllegalVariableNameRule() },
            RuleProvider { BooleanPropertyInteroperabilityRule() },
            RuleProvider { GenericNameRule() },
            // Ordering
            RuleProvider { BlockTagOrderRule() },
            RuleProvider { CommonFunctionPositionRule() },
            RuleProvider { InnerClassPositionRule() },
            RuleProvider { MemberOrderRule() },
            RuleProvider { OverloadFunctionPositionRule() },
            // Scripting
            RuleProvider { CentralizedDependencyRule() },
            RuleProvider { LazyApiRule() },
            RuleProvider { RootProjectNameRule() },
            RuleProvider { ScriptFileNameRule() },
            RuleProvider { NoLonelyConfigurationRule() },
            // Spacing
            RuleProvider { BlankLineBeforeBlockTagsRule() },
            RuleProvider { BlockCommentSpacingRule() },
            RuleProvider { BlockTagIndentationRule() },
            // Stating
            RuleProvider { NoBroadThrowRule() },
            RuleProvider { NoLonelyCaseRule() },
            RuleProvider { NoLonelyIfRule() },
            RuleProvider { NoNestedIfElseRule() },
            RuleProvider { NoRedundantDefaultRule() },
            RuleProvider { NoRedundantElseRule() },
            RuleProvider { NoRedundantIfRule() },
            RuleProvider { NoUselessContinueRule() },
            RuleProvider { NoUselessReturnRule() },
            RuleProvider { ShorthandAssignmentRule() },
            // Testing
            RuleProvider { NoConfusingAssertionRule() },
            RuleProvider { NoDeprecatedAnnotationRule() },
            RuleProvider { SpecificAssertionRule() },
            // Trimming
            RuleProvider { BlockCommentTrimmingRule() },
            RuleProvider { CommentTrimmingRule() },
            RuleProvider { NoDuplicateBlankLineInBlockCommentRule() },
            RuleProvider { NoDuplicateBlankLineInCommentRule() },
            RuleProvider { NoLeadingBlankLineRule() },
            // Wrapping
            RuleProvider { ElvisWrappingRule() },
            RuleProvider { InfixCallWrappingRule() },
        )

    internal companion object {
        val ID: RuleSetId = RuleSetId("rulebook")
    }
}
