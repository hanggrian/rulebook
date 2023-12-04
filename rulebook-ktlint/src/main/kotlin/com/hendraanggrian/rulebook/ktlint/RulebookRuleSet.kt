package com.hendraanggrian.rulebook.ktlint

import com.pinterest.ktlint.cli.ruleset.core.api.RuleSetProviderV3
import com.pinterest.ktlint.rule.engine.core.api.RuleProvider

public class RulebookRuleSet : RuleSetProviderV3(RulebookRule.ID) {
    override fun getRuleProviders(): Set<RuleProvider> =
        setOf(
            RuleProvider { AcronymCapitalizationRule() },
            RuleProvider { ConstructorPositionRule() },
            RuleProvider { StaticInitializerPositionRule() },
            RuleProvider { BlockCommentSpacingRule() },
            RuleProvider { BlockTagPunctuationRule() },
            RuleProvider { EmptyBlockWrappingRule() },
            RuleProvider { ExceptionThrowingRule() },
            RuleProvider { FileSizingRule() },
            RuleProvider { FunctionExpressionRule() },
            RuleProvider { GenericsNamingRule() },
            RuleProvider { IfStatementNestingRule() },
            RuleProvider { JavaApiUsageRule() },
            RuleProvider { ObjectsComparisonRule() },
            RuleProvider { QualifierRedundancyRule() },
            RuleProvider { StringInterpolationRule() },
            RuleProvider { SwitchCasesSpacingRule() },
            RuleProvider { WordMeaningRule() },
        )
}
