package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Messages
import com.hanggrian.rulebook.ktlint.internals.contains
import com.hanggrian.rulebook.ktlint.internals.isMultiline
import com.pinterest.ktlint.rule.engine.core.api.ElementType.BINARY_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.OPERATION_REFERENCE
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.isWhiteSpaceWithNewline
import com.pinterest.ktlint.rule.engine.core.api.isWhiteSpaceWithoutNewline
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/** [See detail](https://hanggrian.github.io/rulebook/rules/all/#infix-call-wrap) */
public class InfixCallWrapRule : RulebookRule(ID) {
    override val tokens: TokenSet = TokenSet.create(OPERATION_REFERENCE)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // filter infix function call
        node
            .takeIf { IDENTIFIER in it }
            ?: return

        // target multiline statement
        val parent =
            node
                .treeParent
                .takeIf { it.elementType == BINARY_EXPRESSION && it.isMultiline() }
                ?: return

        // checks for violation
        if (node.treePrev.isWhiteSpaceWithNewline()) {
            emit(node.startOffset, Messages.get(MSG_UNEXPECTED, node.text), false)
            return
        }
        node
            .treeNext
            .takeIf { it.isWhiteSpaceWithoutNewline() }
            ?: return
        emit(parent.lastChildNode.startOffset, Messages.get(MSG_MISSING, node.text), false)
    }

    internal companion object {
        val ID = RuleId("${RulebookRuleSet.ID.value}:infix-call-wrap")

        const val MSG_MISSING = "infix.call.wrap.missing"
        const val MSG_UNEXPECTED = "infix.call.wrap.unexpected"
    }
}
