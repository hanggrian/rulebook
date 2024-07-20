package com.hanggrian.rulebook.ktlint

import com.pinterest.ktlint.rule.engine.core.api.AutocorrectDecision
import com.pinterest.ktlint.rule.engine.core.api.Rule
import com.pinterest.ktlint.rule.engine.core.api.RuleAutocorrectApproveHandler
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.RuleSetId
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.EditorConfigProperty
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

public typealias Emit = (
    offset: Int,
    errorMessage: String,
    canBeAutoCorrected: Boolean,
) -> AutocorrectDecision

public abstract class Rule(
    id: String,
    final override val usesEditorConfigProperties: Set<EditorConfigProperty<*>> = emptySet(),
    final override val visitorModifiers: Set<VisitorModifier> = emptySet(),
) : Rule(
        RuleId("${ID.value}:$id"),
        About(
            maintainer = "Hendra Anggrian",
            repositoryUrl = "https://github.com/hanggrian/rulebook",
            issueTrackerUrl = "https://github.com/hanggrian/rulebook/issues",
        ),
        visitorModifiers,
        usesEditorConfigProperties,
    ),
    RuleAutocorrectApproveHandler {
    public abstract val tokens: TokenSet

    public abstract fun visitToken(node: ASTNode, emit: Emit)

    final override fun beforeVisitChildNodes(node: ASTNode, emit: Emit) {
        if (node.elementType in tokens) {
            visitToken(node, emit)
        }
    }

    public companion object {
        public val ID: RuleSetId = RuleSetId("rulebook")
    }
}
