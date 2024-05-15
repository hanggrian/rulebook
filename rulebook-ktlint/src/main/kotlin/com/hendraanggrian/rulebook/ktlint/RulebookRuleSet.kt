package com.hendraanggrian.rulebook.ktlint

import com.pinterest.ktlint.cli.ruleset.core.api.RuleSetProviderV3
import com.pinterest.ktlint.rule.engine.core.api.RuleProvider

public class RulebookRuleSet : RuleSetProviderV3(RulebookRule.ID) {
    public override fun getRuleProviders(): Set<RuleProvider> =
        setOf(
            RuleProvider { BlockCommentSpacingRule() },
            RuleProvider { BlockTagPunctuationRule() },
            RuleProvider { BlockTagsInitialSpacingRule() },
            RuleProvider { ConstructorPositionRule() },
            RuleProvider { EmptyBlockWrappingRule() },
            RuleProvider { ExceptionThrowingRule() },
            RuleProvider { FileInitialWrappingRule() },
            RuleProvider { FileSizeLimitationRule() },
            RuleProvider { FunctionExpressionRule() },
            RuleProvider { GenericsNamingRule() },
            RuleProvider { IfStatementNestingRule() },
            RuleProvider { KotlinApiConsistencyRule() },
            RuleProvider { ObjectsComparisonRule() },
            RuleProvider { PropertyIdiomaticNamingRule() },
            RuleProvider { QualifierRedundancyRule() },
            RuleProvider { SourceAcronymCapitalizationRule() },
            RuleProvider { SourceWordMeaningRule() },
            RuleProvider { StaticClassPositionRule() },
            RuleProvider { StringInterpolationRule() },
            RuleProvider { SwitchStatementWrappingRule() },
            RuleProvider { TodoCommentFormattingRule() },
        )
}
