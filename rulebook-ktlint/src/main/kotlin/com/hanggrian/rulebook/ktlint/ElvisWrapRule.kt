package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Messages
import com.hanggrian.rulebook.ktlint.internals.isMultiline
import com.hanggrian.rulebook.ktlint.internals.lastMostChild
import com.pinterest.ktlint.rule.engine.core.api.ElementType.ELVIS
import com.pinterest.ktlint.rule.engine.core.api.ElementType.OPERATION_REFERENCE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.RBRACE
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.isWhiteSpaceWithNewline
import com.pinterest.ktlint.rule.engine.core.api.isWhiteSpaceWithoutNewline
import com.pinterest.ktlint.rule.engine.core.api.prevCodeSibling
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/** [See detail](https://hanggrian.github.io/rulebook/rules/#elvis-wrap) */
public class ElvisWrapRule : RulebookRule(ID) {
    override val tokens: TokenSet = TokenSet.create(ELVIS)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // target multiline statement
        val operationReference =
            node
                .treeParent
                .takeIf { it.elementType == OPERATION_REFERENCE && it.treeParent.isMultiline() }
                ?: return

        // checks for violation
        val sibling =
            operationReference
                .prevCodeSibling()
                ?.lastMostChild
                ?: return
        if (sibling.elementType == RBRACE &&
            sibling.treePrev.isWhiteSpaceWithNewline()
        ) {
            operationReference
                .takeIf { it.treePrev.isWhiteSpaceWithNewline() }
                ?: return
            emit(node.startOffset, Messages[MSG_UNEXPECTED], false)
            return
        }
        operationReference
            .takeIf { it.treePrev.isWhiteSpaceWithoutNewline() }
            ?: return
        emit(node.startOffset, Messages[MSG_MISSING], false)
    }

    public companion object {
        public val ID: RuleId = RuleId("${RulebookRuleSet.ID.value}:elvis-wrap")

        private const val MSG_MISSING = "elvis.wrap.missing"
        private const val MSG_UNEXPECTED = "elvis.wrap.unexpected"
    }
}
