package com.hendraanggrian.rulebook.ktlint

import com.pinterest.ktlint.cli.ruleset.core.api.RuleSetProviderV3
import com.pinterest.ktlint.rule.engine.core.api.RuleProvider

public class RulebookRuleSet : RuleSetProviderV3(RulebookRule.ID) {
    override fun getRuleProviders(): Set<RuleProvider> =
        setOf(
            RuleProvider { BlockCommentSpacingRule() },
            RuleProvider { BlockTagPunctuationRule() },
            RuleProvider { BlockTagsInitialSpacingRule() },
            RuleProvider { ConstructorPositionRule() },
            RuleProvider { EmptyCodeBlockWrappingRule() },
            RuleProvider { ExceptionSubclassThrowingRule() },
            RuleProvider { FileInitialWrappingRule() },
            RuleProvider { FileSizeLimitationRule() },
            RuleProvider { FunctionSingleExpressionRule() },
            RuleProvider { GenericsCommonNamingRule() },
            RuleProvider { IfStatementNestingRule() },
            RuleProvider { KotlinApiConsistencyRule() },
            RuleProvider { ObjectStructuralComparisonRule() },
            RuleProvider { PropertyIllegalNamingRule() },
            RuleProvider { QualifierRedundancyRule() },
            RuleProvider { SourceAcronymCapitalizationRule() },
            RuleProvider { SourceWordMeaningRule() },
            RuleProvider { StaticClassPositionRule() },
            RuleProvider { SwitchStatementWrappingRule() },
            RuleProvider { TodoCommentFormattingRule() },
        )
}
