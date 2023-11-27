package com.hendraanggrian.rulebook.ktlint

import com.pinterest.ktlint.cli.ruleset.core.api.RuleSetProviderV3
import com.pinterest.ktlint.rule.engine.core.api.RuleProvider

public class RulebookRuleSet : RuleSetProviderV3(RulebookRule.ID) {
    override fun getRuleProviders(): Set<RuleProvider> =
        setOf(
            RuleProvider { AddBlankLineBeforeTagsRule() },
            RuleProvider { AvoidMeaninglessWordRule() },
            RuleProvider { CapitalizeFirstAcronymLetterRule() },
            RuleProvider { EndBlockTagWithPeriodRule() },
            RuleProvider { InvertIfConditionRule() },
            RuleProvider { ReduceFileLengthRule() },
            RuleProvider { RemoveBlankLineBetweenCasesRule() },
            RuleProvider { RemoveRedundantQualifierRule() },
            RuleProvider { RenameUncommonGenericsRule() },
            RuleProvider { ThrowNarrowerTypeRule() },
            RuleProvider { UseExpressionFunctionRule() },
            RuleProvider { UseKotlinApiRule() },
            RuleProvider { UseStringTemplatesRule() },
            RuleProvider { UseStructuralEqualityRule() },
            RuleProvider { WrapEmptyBlockRule() },
        )
}
