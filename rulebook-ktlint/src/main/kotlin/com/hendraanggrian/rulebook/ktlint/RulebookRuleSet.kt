package com.hendraanggrian.rulebook.ktlint

import com.pinterest.ktlint.cli.ruleset.core.api.RuleSetProviderV3
import com.pinterest.ktlint.rule.engine.core.api.RuleProvider

public class RulebookRuleSet : RuleSetProviderV3(Rule.ID) {
    override fun getRuleProviders(): Set<RuleProvider> =
        setOf(
            RuleProvider { BlockCommentLineTrimmingRule() },
            RuleProvider { BlockCommentSpacingRule() },
            RuleProvider { BlockTagDescriptionPunctuationRule() },
            RuleProvider { BlockTagInitialLineSpacingRule() },
            RuleProvider { ClassFinalNameBlacklistingRule() },
            RuleProvider { ClassNameAcronymCapitalizationRule() },
            RuleProvider { ClassOrganizationRule() },
            RuleProvider { ConditionalBranchLineWrappingRule() },
            RuleProvider { EmptyBlockCommentLineJoiningRule() },
            RuleProvider { EmptyCodeBlockWrappingRule() },
            RuleProvider { EmptyCommentLineJoiningRule() },
            RuleProvider { ErrorSubclassThrowingRule() },
            RuleProvider { FileInitialLineTrimmingRule() },
            RuleProvider { FileSizeLimitationRule() },
            RuleProvider { GenericsNameWhitelistingRule() },
            RuleProvider { IdentifierNameBlacklistingRule() },
            RuleProvider { IfStatementFlatteningRule() },
            RuleProvider { InnerClassPositionRule() },
            RuleProvider { KotlinApiPriorityRule() },
            RuleProvider { OperandStructuralEqualityRule() },
            RuleProvider { QualifierConsistencyRule() },
            RuleProvider { SpecialFunctionPositionRule() },
            RuleProvider { TodoCommentStylingRule() },
        )
}
