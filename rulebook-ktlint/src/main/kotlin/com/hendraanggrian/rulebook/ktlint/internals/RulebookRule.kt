package com.hendraanggrian.rulebook.ktlint.internals

import com.pinterest.ktlint.rule.engine.core.api.Rule
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.RuleSetId
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.EditorConfigProperty

open class RulebookRule internal constructor(
    id: String,
    override val visitorModifiers: Set<VisitorModifier> = emptySet(),
    override val usesEditorConfigProperties: Set<EditorConfigProperty<*>> = emptySet(),
) : Rule(
        ruleId = RuleId("${ID.value}:$id"),
        visitorModifiers = visitorModifiers,
        usesEditorConfigProperties = usesEditorConfigProperties,
        about =
            About(
                maintainer = "Rulebook",
                repositoryUrl = "https://github.com/hendraanggrian/rulebook",
                issueTrackerUrl = "https://github.com/hendraanggrian/rulebook/issues",
            ),
    ) {
    companion object {
        val ID = RuleSetId("rulebook")
    }
}
