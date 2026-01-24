package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.Messages
import com.hanggrian.rulebook.ktlint.RulebookRuleSet
import com.hanggrian.rulebook.ktlint.isMultiline
import com.hanggrian.rulebook.ktlint.lastLeaf
import com.pinterest.ktlint.rule.engine.core.api.ElementType.ELVIS
import com.pinterest.ktlint.rule.engine.core.api.ElementType.OPERATION_REFERENCE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.RBRACE
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.isWhiteSpaceWithNewline20
import com.pinterest.ktlint.rule.engine.core.api.isWhiteSpaceWithoutNewline20
import com.pinterest.ktlint.rule.engine.core.api.prevCodeSibling20
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
                .prevCodeSibling20
                ?.lastLeaf()
                ?: return
        if (sibling.elementType == RBRACE &&
            sibling.treePrev.isWhiteSpaceWithNewline20
        ) {
            operationReference
                .treePrev
                .takeIf { it.isWhiteSpaceWithNewline20 }
                ?: return
            emit(node.startOffset, Messages[MSG_UNEXPECTED], false)
            return
        }
        operationReference
            .treePrev
            .takeIf { it.isWhiteSpaceWithoutNewline20 }
            ?: return
        emit(node.startOffset, Messages[MSG_MISSING], false)
    }

    public companion object {
        public val ID: RuleId = RuleId("${RulebookRuleSet.ID.value}:elvis-wrap")

        private const val MSG_MISSING = "elvis.wrap.missing"
        private const val MSG_UNEXPECTED = "elvis.wrap.unexpected"
    }
}
