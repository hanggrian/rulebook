package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.rules.AbbreviationAsWordRule
import com.hanggrian.rulebook.ktlint.rules.AbstractFunctionInAbstractClassRule
import com.hanggrian.rulebook.ktlint.rules.BlockCommentClippingRule
import com.hanggrian.rulebook.ktlint.rules.BlockCommentSpacesRule
import com.hanggrian.rulebook.ktlint.rules.BlockCommentTrimRule
import com.hanggrian.rulebook.ktlint.rules.BlockTagIndentationRule
import com.hanggrian.rulebook.ktlint.rules.BlockTagOrderRule
import com.hanggrian.rulebook.ktlint.rules.BlockTagPunctuationRule
import com.hanggrian.rulebook.ktlint.rules.BooleanPropertyInteroperabilityRule
import com.hanggrian.rulebook.ktlint.rules.BracesClippingRule
import com.hanggrian.rulebook.ktlint.rules.CommentTrimRule
import com.hanggrian.rulebook.ktlint.rules.CommonFunctionPositionRule
import com.hanggrian.rulebook.ktlint.rules.ComplicatedAssertionRule
import com.hanggrian.rulebook.ktlint.rules.ComplicatedAssignmentRule
import com.hanggrian.rulebook.ktlint.rules.ConfusingAssertionRule
import com.hanggrian.rulebook.ktlint.rules.DecentralizedDependencyRule
import com.hanggrian.rulebook.ktlint.rules.DeprecatedAnnotationRule
import com.hanggrian.rulebook.ktlint.rules.DuplicateBlankLineInBlockCommentRule
import com.hanggrian.rulebook.ktlint.rules.DuplicateBlankLineInCommentRule
import com.hanggrian.rulebook.ktlint.rules.EagerApiRule
import com.hanggrian.rulebook.ktlint.rules.ElvisWrapRule
import com.hanggrian.rulebook.ktlint.rules.ExceptionInheritanceRule
import com.hanggrian.rulebook.ktlint.rules.FileSizeRule
import com.hanggrian.rulebook.ktlint.rules.GenericNameRule
import com.hanggrian.rulebook.ktlint.rules.IllegalThrowRule
import com.hanggrian.rulebook.ktlint.rules.IllegalVariableNameRule
import com.hanggrian.rulebook.ktlint.rules.InfixCallWrapRule
import com.hanggrian.rulebook.ktlint.rules.InlinedContractFunctionRule
import com.hanggrian.rulebook.ktlint.rules.InnerClassPositionRule
import com.hanggrian.rulebook.ktlint.rules.LiteralComparisonRule
import com.hanggrian.rulebook.ktlint.rules.LonelyCaseRule
import com.hanggrian.rulebook.ktlint.rules.LonelyConfigurationRule
import com.hanggrian.rulebook.ktlint.rules.LonelyIfRule
import com.hanggrian.rulebook.ktlint.rules.LowercaseFRule
import com.hanggrian.rulebook.ktlint.rules.LowercaseHexRule
import com.hanggrian.rulebook.ktlint.rules.MeaninglessWordRule
import com.hanggrian.rulebook.ktlint.rules.MemberOrderRule
import com.hanggrian.rulebook.ktlint.rules.MissingBlankLineBeforeBlockTagsRule
import com.hanggrian.rulebook.ktlint.rules.NestedIfElseRule
import com.hanggrian.rulebook.ktlint.rules.NoConfusingPredicateRule
import com.hanggrian.rulebook.ktlint.rules.NoDeprecatedTypeRule
import com.hanggrian.rulebook.ktlint.rules.OverloadFunctionPositionRule
import com.hanggrian.rulebook.ktlint.rules.RedundantDefaultRule
import com.hanggrian.rulebook.ktlint.rules.RedundantElseRule
import com.hanggrian.rulebook.ktlint.rules.RedundantIfRule
import com.hanggrian.rulebook.ktlint.rules.RootProjectNameRule
import com.hanggrian.rulebook.ktlint.rules.ScriptFileNameRule
import com.hanggrian.rulebook.ktlint.rules.SimpleBooleanExpressionRule
import com.hanggrian.rulebook.ktlint.rules.SimpleSizeComparisonRule
import com.hanggrian.rulebook.ktlint.rules.TodoCommentStyleRule
import com.hanggrian.rulebook.ktlint.rules.UnnecessaryContinueRule
import com.hanggrian.rulebook.ktlint.rules.UnnecessaryLeadingBlankLineRule
import com.hanggrian.rulebook.ktlint.rules.UnnecessaryReturnRule
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
            RuleProvider { FileSizeRule() },
            // Naming
            RuleProvider { AbbreviationAsWordRule() },
            RuleProvider { MeaninglessWordRule() },
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
            RuleProvider { DecentralizedDependencyRule() },
            RuleProvider { EagerApiRule() },
            RuleProvider { RootProjectNameRule() },
            RuleProvider { ScriptFileNameRule() },
            RuleProvider { LonelyConfigurationRule() },
            // Spacing
            RuleProvider { BlockCommentSpacesRule() },
            RuleProvider { BlockTagIndentationRule() },
            RuleProvider { MissingBlankLineBeforeBlockTagsRule() },
            RuleProvider { UnnecessaryLeadingBlankLineRule() },
            // Stating
            RuleProvider { ComplicatedAssignmentRule() },
            RuleProvider { IllegalThrowRule() },
            RuleProvider { LonelyCaseRule() },
            RuleProvider { LonelyIfRule() },
            RuleProvider { NestedIfElseRule() },
            RuleProvider { RedundantDefaultRule() },
            RuleProvider { RedundantElseRule() },
            RuleProvider { RedundantIfRule() },
            RuleProvider { UnnecessaryContinueRule() },
            RuleProvider { UnnecessaryReturnRule() },
            // Testing
            RuleProvider { ComplicatedAssertionRule() },
            RuleProvider { ConfusingAssertionRule() },
            RuleProvider { DeprecatedAnnotationRule() },
            // Trimming
            RuleProvider { BlockCommentTrimRule() },
            RuleProvider { CommentTrimRule() },
            RuleProvider { DuplicateBlankLineInBlockCommentRule() },
            RuleProvider { DuplicateBlankLineInCommentRule() },
            // Wrapping
            RuleProvider { ElvisWrapRule() },
            RuleProvider { InfixCallWrapRule() },
        )

    internal companion object {
        val ID: RuleSetId = RuleSetId("rulebook")
    }
}
