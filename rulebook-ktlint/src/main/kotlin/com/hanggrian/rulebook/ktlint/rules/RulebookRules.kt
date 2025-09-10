package com.hanggrian.rulebook.ktlint.rules

import com.pinterest.ktlint.rule.engine.core.api.AutocorrectDecision
import com.pinterest.ktlint.rule.engine.core.api.Rule
import com.pinterest.ktlint.rule.engine.core.api.RuleAutocorrectApproveHandler
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.EditorConfigProperty
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

public typealias Emit = (
    offset: Int,
    errorMessage: String,
    canBeAutoCorrected: Boolean,
) -> AutocorrectDecision

public abstract class RulebookRule(
    public override val ruleId: RuleId,
    public override val usesEditorConfigProperties: Set<EditorConfigProperty<*>> = emptySet(),
    public override val visitorModifiers: Set<VisitorModifier> = emptySet(),
) : Rule(
        ruleId,
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

    public constructor(ruleId: RuleId, vararg usesEditorConfigProperties: EditorConfigProperty<*>) :
        this(ruleId, setOf(*usesEditorConfigProperties))

    public abstract fun visitToken(node: ASTNode, emit: Emit)

    final override fun beforeVisitChildNodes(node: ASTNode, emit: Emit) {
        if (node.elementType in tokens) {
            visitToken(node, emit)
        }
    }
}
