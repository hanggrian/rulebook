package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.rules.AbbreviationAsWordRule
import com.hanggrian.rulebook.ktlint.rules.BlockCommentClipRule
import com.hanggrian.rulebook.ktlint.rules.BlockCommentSpacesRule
import com.hanggrian.rulebook.ktlint.rules.BlockCommentTrimRule
import com.hanggrian.rulebook.ktlint.rules.BlockTagIndentationRule
import com.hanggrian.rulebook.ktlint.rules.BlockTagOrderRule
import com.hanggrian.rulebook.ktlint.rules.BlockTagPunctuationRule
import com.hanggrian.rulebook.ktlint.rules.BooleanPropertyInteropRule
import com.hanggrian.rulebook.ktlint.rules.BracesClipRule
import com.hanggrian.rulebook.ktlint.rules.CommentTrimRule
import com.hanggrian.rulebook.ktlint.rules.CommonFunctionPositionRule
import com.hanggrian.rulebook.ktlint.rules.ConfusingPredicateRule
import com.hanggrian.rulebook.ktlint.rules.DeprecatedTypeRule
import com.hanggrian.rulebook.ktlint.rules.DuplicateBlankLineInBlockCommentRule
import com.hanggrian.rulebook.ktlint.rules.DuplicateBlankLineInCommentRule
import com.hanggrian.rulebook.ktlint.rules.ElvisWrapRule
import com.hanggrian.rulebook.ktlint.rules.ExceptionInheritanceRule
import com.hanggrian.rulebook.ktlint.rules.FileSizeRule
import com.hanggrian.rulebook.ktlint.rules.GenericNameRule
import com.hanggrian.rulebook.ktlint.rules.IllegalThrowRule
import com.hanggrian.rulebook.ktlint.rules.IllegalVariableNameRule
import com.hanggrian.rulebook.ktlint.rules.InfixCallWrapRule
import com.hanggrian.rulebook.ktlint.rules.InnerClassPositionRule
import com.hanggrian.rulebook.ktlint.rules.LowercaseFRule
import com.hanggrian.rulebook.ktlint.rules.MeaninglessWordRule
import com.hanggrian.rulebook.ktlint.rules.MemberOrderRule
import com.hanggrian.rulebook.ktlint.rules.MissingBlankLineBeforeBlockTagsRule
import com.hanggrian.rulebook.ktlint.rules.MissingInlineInContractRule
import com.hanggrian.rulebook.ktlint.rules.NestedIfElseRule
import com.hanggrian.rulebook.ktlint.rules.NullEqualityRule
import com.hanggrian.rulebook.ktlint.rules.OverloadFunctionPositionRule
import com.hanggrian.rulebook.ktlint.rules.RedundantDefaultRule
import com.hanggrian.rulebook.ktlint.rules.RedundantElseRule
import com.hanggrian.rulebook.ktlint.rules.TodoCommentRule
import com.hanggrian.rulebook.ktlint.rules.UnnecessaryAbstractRule
import com.hanggrian.rulebook.ktlint.rules.UnnecessaryInitialBlankLineRule
import com.hanggrian.rulebook.ktlint.rules.UnnecessarySwitchRule
import com.pinterest.ktlint.cli.ruleset.core.api.RuleSetProviderV3
import com.pinterest.ktlint.rule.engine.core.api.RuleProvider
import com.pinterest.ktlint.rule.engine.core.api.RuleSetId

public class RulebookRuleSet : RuleSetProviderV3(ID) {
    override fun getRuleProviders(): Set<RuleProvider> =
        setOf(
            RuleProvider { BlockTagPunctuationRule() },
            RuleProvider { ConfusingPredicateRule() },
            RuleProvider { DeprecatedTypeRule() },
            RuleProvider { FileSizeRule() },
            RuleProvider { NullEqualityRule() },
            RuleProvider { TodoCommentRule() },
            // Clipping
            RuleProvider { BlockCommentClipRule() },
            RuleProvider { BracesClipRule() },
            // Declaring
            RuleProvider { ExceptionInheritanceRule() },
            RuleProvider { LowercaseFRule() },
            RuleProvider { MissingInlineInContractRule() },
            RuleProvider { UnnecessaryAbstractRule() },
            // Naming
            RuleProvider { AbbreviationAsWordRule() },
            RuleProvider { MeaninglessWordRule() },
            RuleProvider { IllegalVariableNameRule() },
            RuleProvider { BooleanPropertyInteropRule() },
            RuleProvider { GenericNameRule() },
            // Ordering
            RuleProvider { BlockTagOrderRule() },
            RuleProvider { CommonFunctionPositionRule() },
            RuleProvider { InnerClassPositionRule() },
            RuleProvider { MemberOrderRule() },
            RuleProvider { OverloadFunctionPositionRule() },
            // Spacing
            RuleProvider { BlockCommentSpacesRule() },
            RuleProvider { BlockTagIndentationRule() },
            RuleProvider { MissingBlankLineBeforeBlockTagsRule() },
            RuleProvider { UnnecessaryInitialBlankLineRule() },
            // Stating
            RuleProvider { IllegalThrowRule() },
            RuleProvider { NestedIfElseRule() },
            RuleProvider { RedundantDefaultRule() },
            RuleProvider { RedundantElseRule() },
            RuleProvider { UnnecessarySwitchRule() },
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
