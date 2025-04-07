package com.hanggrian.rulebook.ktlint

import com.pinterest.ktlint.cli.ruleset.core.api.RuleSetProviderV3
import com.pinterest.ktlint.rule.engine.core.api.RuleProvider
import com.pinterest.ktlint.rule.engine.core.api.RuleSetId

public class RulebookRuleSet : RuleSetProviderV3(ID) {
    override fun getRuleProviders(): Set<RuleProvider> =
        setOf(
            // Basic
            RuleProvider { BlockTagPunctuationRule() },
            RuleProvider { BuiltInTypesRule() },
            RuleProvider { ConfusingPredicateRule() },
            RuleProvider { FileSizeRule() },
            RuleProvider { NullEqualityRule() },
            RuleProvider { TodoCommentRule() },
            // Declaring
            RuleProvider { AbstractClassDefinitionRule() },
            RuleProvider { ContractFunctionDefinitionRule() },
            RuleProvider { ExceptionInheritanceRule() },
            RuleProvider { NumberSuffixForFloatRule() },
            // Naming
            RuleProvider { ClassNameAcronymRule() },
            RuleProvider { IllegalClassFinalNameRule() },
            RuleProvider { IllegalVariableNameRule() },
            RuleProvider { PropertyNameInteropRule() },
            RuleProvider { RequiredGenericNameRule() },
            // Ordering
            RuleProvider { BlockTagOrderRule() },
            RuleProvider { BuiltInFunctionPositionRule() },
            RuleProvider { InnerClassPositionRule() },
            RuleProvider { MemberOrderRule() },
            RuleProvider { OverloadFunctionPositionRule() },
            // Spacing
            RuleProvider { BlockCommentSpacesRule() },
            RuleProvider { BlockCommentTrimRule() },
            RuleProvider { BlockTagIndentationRule() },
            RuleProvider { MissingBlankLineBeforeBlockTagsRule() },
            RuleProvider { CaseSeparatorRule() },
            RuleProvider { CommentTrimRule() },
            RuleProvider { DuplicateBlankLineInBlockCommentRule() },
            RuleProvider { DuplicateBlankLineInCommentRule() },
            RuleProvider { UnnecessaryBlankLineBeforePackageRule() },
            // Stating
            RuleProvider { IllegalThrowRule() },
            RuleProvider { NestedIfElseRule() },
            RuleProvider { RedundantDefaultRule() },
            RuleProvider { RedundantElseRule() },
            RuleProvider { UnnecessarySwitchRule() },
            // Wrapping
            RuleProvider { ShortBlockCommentJoinRule() },
            RuleProvider { ElvisWrapRule() },
            RuleProvider { EmptyCodeBlockJoinRule() },
            RuleProvider { InfixCallWrapRule() },
        )

    internal companion object {
        val ID: RuleSetId = RuleSetId("rulebook")
    }
}
