package com.hendraanggrian.rulebook.ktlint

import com.pinterest.ktlint.cli.ruleset.core.api.RuleSetProviderV3
import com.pinterest.ktlint.rule.engine.core.api.RuleProvider

public class RulebookRuleSet : RuleSetProviderV3(RulebookRule.ID) {
    override fun getRuleProviders(): Set<RuleProvider> =
        setOf(
            RuleProvider { BlockCommentSpacingRule() },
            RuleProvider { BlockTagPunctuationRule() },
            RuleProvider { BlockTagGroupInitialSpacingRule() },
            RuleProvider { ClassNameAcronymCapitalizationRule() },
            RuleProvider { ClassNameBlacklistingRule() },
            RuleProvider { ConstructorPositionRule() },
            RuleProvider { EmptyCodeBlockWrappingRule() },
            RuleProvider { ExceptionSubclassThrowingRule() },
            RuleProvider { FileInitialWrappingRule() },
            RuleProvider { FileSizeLimitationRule() },
            RuleProvider { FunctionSingleExpressionRule() },
            RuleProvider { GenericsNameWhitelistingRule() },
            RuleProvider { IdentifierNameBlacklistingRule() },
            RuleProvider { IfStatementFlatteningRule() },
            RuleProvider { KotlinApiPriorityRule() },
            RuleProvider { ObjectStructuralComparisonRule() },
            RuleProvider { QualifierConsistencyRule() },
            RuleProvider { StaticClassPositionRule() },
            RuleProvider { SwitchStatementWrappingRule() },
            RuleProvider { TodoCommentStylingRule() },
        )
}
