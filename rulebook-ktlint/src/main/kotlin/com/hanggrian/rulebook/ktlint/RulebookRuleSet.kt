package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.rules.AbstractClassDefinitionRule
import com.hanggrian.rulebook.ktlint.rules.BlockCommentSpacesRule
import com.hanggrian.rulebook.ktlint.rules.BlockCommentTrimRule
import com.hanggrian.rulebook.ktlint.rules.BlockTagIndentationRule
import com.hanggrian.rulebook.ktlint.rules.BlockTagOrderRule
import com.hanggrian.rulebook.ktlint.rules.BlockTagPunctuationRule
import com.hanggrian.rulebook.ktlint.rules.BuiltInFunctionPositionRule
import com.hanggrian.rulebook.ktlint.rules.BuiltInTypesRule
import com.hanggrian.rulebook.ktlint.rules.ClassNameAbbreviationRule
import com.hanggrian.rulebook.ktlint.rules.CommentTrimRule
import com.hanggrian.rulebook.ktlint.rules.ConfusingPredicateRule
import com.hanggrian.rulebook.ktlint.rules.ContractFunctionDefinitionRule
import com.hanggrian.rulebook.ktlint.rules.DuplicateBlankLineInBlockCommentRule
import com.hanggrian.rulebook.ktlint.rules.DuplicateBlankLineInCommentRule
import com.hanggrian.rulebook.ktlint.rules.ElvisWrapRule
import com.hanggrian.rulebook.ktlint.rules.EmptyBracesClipRule
import com.hanggrian.rulebook.ktlint.rules.ExceptionInheritanceRule
import com.hanggrian.rulebook.ktlint.rules.FileSizeRule
import com.hanggrian.rulebook.ktlint.rules.IllegalClassNameSuffixRule
import com.hanggrian.rulebook.ktlint.rules.IllegalThrowRule
import com.hanggrian.rulebook.ktlint.rules.IllegalVariableNameRule
import com.hanggrian.rulebook.ktlint.rules.InfixCallWrapRule
import com.hanggrian.rulebook.ktlint.rules.InnerClassPositionRule
import com.hanggrian.rulebook.ktlint.rules.MemberOrderRule
import com.hanggrian.rulebook.ktlint.rules.MissingBlankLineBeforeBlockTagsRule
import com.hanggrian.rulebook.ktlint.rules.NestedIfElseRule
import com.hanggrian.rulebook.ktlint.rules.NullEqualityRule
import com.hanggrian.rulebook.ktlint.rules.NumberSuffixForFloatRule
import com.hanggrian.rulebook.ktlint.rules.OverloadFunctionPositionRule
import com.hanggrian.rulebook.ktlint.rules.PropertyNameInteropRule
import com.hanggrian.rulebook.ktlint.rules.RedundantDefaultRule
import com.hanggrian.rulebook.ktlint.rules.RedundantElseRule
import com.hanggrian.rulebook.ktlint.rules.RequiredGenericsNameRule
import com.hanggrian.rulebook.ktlint.rules.ShortBlockCommentClipRule
import com.hanggrian.rulebook.ktlint.rules.TodoCommentRule
import com.hanggrian.rulebook.ktlint.rules.UnnecessaryBlankLineBeforePackageRule
import com.hanggrian.rulebook.ktlint.rules.UnnecessarySwitchRule
import com.pinterest.ktlint.cli.ruleset.core.api.RuleSetProviderV3
import com.pinterest.ktlint.rule.engine.core.api.RuleProvider
import com.pinterest.ktlint.rule.engine.core.api.RuleSetId

public class RulebookRuleSet : RuleSetProviderV3(ID) {
    override fun getRuleProviders(): Set<RuleProvider> =
        setOf(
            RuleProvider { BlockTagPunctuationRule() },
            RuleProvider { BuiltInTypesRule() },
            RuleProvider { ConfusingPredicateRule() },
            RuleProvider { FileSizeRule() },
            RuleProvider { NullEqualityRule() },
            RuleProvider { TodoCommentRule() },
            // Clipping
            RuleProvider { EmptyBracesClipRule() },
            RuleProvider { ShortBlockCommentClipRule() },
            // Declaring
            RuleProvider { AbstractClassDefinitionRule() },
            RuleProvider { ContractFunctionDefinitionRule() },
            RuleProvider { ExceptionInheritanceRule() },
            RuleProvider { NumberSuffixForFloatRule() },
            // Naming
            RuleProvider { ClassNameAbbreviationRule() },
            RuleProvider { IllegalClassNameSuffixRule() },
            RuleProvider { IllegalVariableNameRule() },
            RuleProvider { PropertyNameInteropRule() },
            RuleProvider { RequiredGenericsNameRule() },
            // Ordering
            RuleProvider { BlockTagOrderRule() },
            RuleProvider { BuiltInFunctionPositionRule() },
            RuleProvider { InnerClassPositionRule() },
            RuleProvider { MemberOrderRule() },
            RuleProvider { OverloadFunctionPositionRule() },
            // Spacing
            RuleProvider { BlockCommentSpacesRule() },
            RuleProvider { BlockTagIndentationRule() },
            RuleProvider { MissingBlankLineBeforeBlockTagsRule() },
            RuleProvider { UnnecessaryBlankLineBeforePackageRule() },
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
