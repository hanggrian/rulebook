package com.hendraanggrian.rulebook.ktlint

import com.pinterest.ktlint.cli.ruleset.core.api.RuleSetProviderV3
import com.pinterest.ktlint.rule.engine.core.api.RuleProvider

public class RulebookRuleSet : RuleSetProviderV3(Rule.ID) {
    override fun getRuleProviders(): Set<RuleProvider> =
        setOf(
            RuleProvider { BlockCommentSpacingRule() },
            RuleProvider { BlockTagDescriptionPunctuationRule() },
            RuleProvider { BlockTagSeparatingRule() },
            RuleProvider { ClassNameAcronymCapitalizationRule() },
            RuleProvider { ClassNameBlacklistingRule() },
            RuleProvider { ClassOrganizationRule() },
            RuleProvider { ConditionalBranchJoiningRule() },
            RuleProvider { EmptyBlockCommentLineJoiningRule() },
            RuleProvider { EmptyCodeBlockWrappingRule() },
            RuleProvider { EmptyCommentLineJoiningRule() },
            RuleProvider { ErrorSubclassThrowingRule() },
            RuleProvider { FileInitialJoiningRule() },
            RuleProvider { FileSizeLimitationRule() },
            RuleProvider { FunctionSingleExpressionRule() },
            RuleProvider { GenericsNameWhitelistingRule() },
            RuleProvider { IdentifierNameBlacklistingRule() },
            RuleProvider { InnerClassPositionRule() },
            RuleProvider { IfStatementFlatteningRule() },
            RuleProvider { KotlinApiPriorityRule() },
            RuleProvider { OperandStructuralEqualityRule() },
            RuleProvider { QualifierConsistencyRule() },
            RuleProvider { SpecialFunctionPositionRule() },
            RuleProvider { TodoCommentStylingRule() },
        )
}
