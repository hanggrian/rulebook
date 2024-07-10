package com.hanggrian.rulebook.ktlint

import com.pinterest.ktlint.rule.engine.core.api.Rule
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.RuleSetId
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.EditorConfigProperty

public open class Rule(
    id: String,
    override val usesEditorConfigProperties: Set<EditorConfigProperty<*>> = emptySet(),
    override val visitorModifiers: Set<VisitorModifier> = emptySet(),
) : Rule(
        RuleId("${ID.value}:$id"),
        About(
            maintainer = "Hendra Anggrian",
            repositoryUrl = "https://github.com/hanggrian/rulebook",
            issueTrackerUrl = "https://github.com/hanggrian/rulebook/issues",
        ),
        visitorModifiers,
        usesEditorConfigProperties,
    ) {
    public companion object {
        public val ID: RuleSetId = RuleSetId("rulebook")
    }
}
