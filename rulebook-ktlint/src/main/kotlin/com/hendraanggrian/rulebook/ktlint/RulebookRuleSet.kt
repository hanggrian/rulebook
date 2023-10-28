package com.hendraanggrian.rulebook.ktlint

import com.pinterest.ktlint.cli.ruleset.core.api.RuleSetProviderV3
import com.pinterest.ktlint.rule.engine.core.api.RuleProvider

class RulebookRuleSet : RuleSetProviderV3(RulebookRule.ID) {
    override fun getRuleProviders(): Set<RuleProvider> =
        setOf(
            RuleProvider { AddBlankLineInDocumentationRule() },
            RuleProvider { InvertIfConditionRule() },
            RuleProvider { PunctuateDocumentationTagRule() },
            RuleProvider { RemoveBlankLineInSwitchRule() },
            RuleProvider { RemoveMeaninglessWordRule() },
            RuleProvider { RenameAbbreviationWordRule() },
            RuleProvider { RenameUncommonGenericsRule() },
            RuleProvider { ReplaceWithKotlinApiRule() },
            RuleProvider { ReplaceWithStructuralEqualityRule() },
            RuleProvider { SpecifyTypeExplicitlyRule() },
            RuleProvider { ThrowExceptionSubclassRule() },
        )
}
