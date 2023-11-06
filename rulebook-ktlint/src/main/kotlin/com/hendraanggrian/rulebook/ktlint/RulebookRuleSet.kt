package com.hendraanggrian.rulebook.ktlint

import com.pinterest.ktlint.cli.ruleset.core.api.RuleSetProviderV3
import com.pinterest.ktlint.rule.engine.core.api.RuleProvider

class RulebookRuleSet : RuleSetProviderV3(RulebookRule.ID) {
    override fun getRuleProviders(): Set<RuleProvider> =
        setOf(
            RuleProvider { AddBlankLineBeforeTagsRule() },
            RuleProvider { AvoidMeaninglessWordRule() },
            RuleProvider { CapitalizeFirstAcronymLetterRule() },
            RuleProvider { EndTagWithPeriodRule() },
            RuleProvider { InvertIfConditionRule() },
            RuleProvider { RemoveBlankLineBetweenCasesRule() },
            RuleProvider { SpecifyTypeExplicitlyRule() },
            RuleProvider { ThrowExceptionSubclassRule() },
            RuleProvider { UseCommonGenericsRule() },
            RuleProvider { UseKotlinApiRule() },
            RuleProvider { UseStructuralEqualityRule() },
        )
}
