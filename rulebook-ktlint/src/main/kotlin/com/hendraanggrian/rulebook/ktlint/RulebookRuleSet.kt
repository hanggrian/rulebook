package com.hendraanggrian.rulebook.ktlint

import com.pinterest.ktlint.cli.ruleset.core.api.RuleSetProviderV3
import com.pinterest.ktlint.rule.engine.core.api.RuleProvider

public class RulebookRuleSet : RuleSetProviderV3(RulebookRule.ID) {
    override fun getRuleProviders(): Set<RuleProvider> =
        setOf(
            RuleProvider { AddBlankLineBeforeTagsRule() },
            RuleProvider { AvoidMeaninglessWordRule() },
            RuleProvider { CapitalizeFirstAcronymLetterRule() },
            RuleProvider { EndBlockTagWithPeriod() },
            RuleProvider { InvertIfConditionRule() },
            RuleProvider { RemoveBlankLineBetweenCasesRule() },
            RuleProvider { RemoveRedundantQualifierRule() },
            RuleProvider { ThrowNarrowerExceptionRule() },
            RuleProvider { UseCommonGenericsRule() },
            RuleProvider { UseKotlinApiRule() },
            RuleProvider { UseStringTemplatesRule() },
            RuleProvider { UseStructuralEqualityRule() },
        )
}
