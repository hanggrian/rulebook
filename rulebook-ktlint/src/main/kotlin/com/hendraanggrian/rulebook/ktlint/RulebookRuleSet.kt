package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.docs.AddBlankLineInDocumentationRule
import com.hendraanggrian.rulebook.ktlint.docs.PunctuateDocumentationTagRule
import com.pinterest.ktlint.cli.ruleset.core.api.RuleSetProviderV3
import com.pinterest.ktlint.rule.engine.core.api.Rule
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.RuleProvider
import com.pinterest.ktlint.rule.engine.core.api.RuleSetId
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.EditorConfigProperty

internal val RULEBOOK_ID = RuleSetId("rulebook")
internal val RULEBOOK_ABOUT = Rule.About(
    maintainer = "Rulebook",
    repositoryUrl = "https://github.com/hendraanggrian/rulebook",
    issueTrackerUrl = "https://github.com/hendraanggrian/rulebook/issues"
)

open class RulebookRule internal constructor(
    id: String,
    override val visitorModifiers: Set<VisitorModifier> = emptySet(),
    override val usesEditorConfigProperties: Set<EditorConfigProperty<*>> = emptySet()
) : Rule(
    ruleId = RuleId("${RULEBOOK_ID.value}:$id"),
    visitorModifiers = visitorModifiers,
    usesEditorConfigProperties = usesEditorConfigProperties,
    about = RULEBOOK_ABOUT
)

class RulebookRuleSet : RuleSetProviderV3(RULEBOOK_ID) {
    override fun getRuleProviders(): Set<RuleProvider> = setOf(
        RuleProvider { AddBlankLineInDocumentationRule() },
        RuleProvider { PunctuateDocumentationTagRule() },
        RuleProvider { AddBlankLineInClassRule() },
        RuleProvider { InvertIfConditionRule() },
        RuleProvider { RemoveBlankLineInSwitchRule() },
        RuleProvider { RenameAbbreviationRule() },
        RuleProvider { RenameGenericsRule() },
        RuleProvider { RenameUnderscoreRule() },
        RuleProvider { ReplaceWithAbstractCollectionRule() },
        RuleProvider { ReplaceWithKotlinApiRule() },
        RuleProvider { SpecifyTypeExplicitlyRule() },
        RuleProvider { ThrowSubclassRule() }
    )
}
