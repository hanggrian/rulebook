package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.docs.AddBlankLineInDocumentationRule
import com.hendraanggrian.rulebook.ktlint.docs.PunctuateDocumentationTagRule
import com.hendraanggrian.rulebook.ktlint.internals.RulebookRule
import com.pinterest.ktlint.cli.ruleset.core.api.RuleSetProviderV3
import com.pinterest.ktlint.rule.engine.core.api.RuleProvider

class RulebookRuleSet : RuleSetProviderV3(RulebookRule.ID) {
    override fun getRuleProviders(): Set<RuleProvider> =
        setOf(
            RuleProvider { AddBlankLineInDocumentationRule() },
            RuleProvider { PunctuateDocumentationTagRule() },
            RuleProvider { InvertIfConditionRule() },
            RuleProvider { RemoveBlankLineInSwitchRule() },
            RuleProvider { RenameAbbreviationWordRule() },
            RuleProvider { RenameUncommonGenericsRule() },
            RuleProvider { RenameMeaninglessWordRule() },
            RuleProvider { ReplaceWithKotlinApiRule() },
            RuleProvider { ReplaceWithStructuralEqualityRule() },
            RuleProvider { SpecifyTypeExplicitlyRule() },
            RuleProvider { ThrowExceptionSubclassRule() },
        )
}
