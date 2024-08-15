package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Messages
import com.hanggrian.rulebook.ktlint.internals.isMultiline
import com.hanggrian.rulebook.ktlint.internals.lastMostChild
import com.pinterest.ktlint.rule.engine.core.api.ElementType.ELVIS
import com.pinterest.ktlint.rule.engine.core.api.ElementType.OPERATION_REFERENCE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.RBRACE
import com.pinterest.ktlint.rule.engine.core.api.isWhiteSpaceWithNewline
import com.pinterest.ktlint.rule.engine.core.api.isWhiteSpaceWithoutNewline
import com.pinterest.ktlint.rule.engine.core.api.prevCodeSibling
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#elvis-wrapping)
 */
public class ElvisWrappingRule : Rule("elvis-wrapping") {
    override val tokens: TokenSet = TokenSet.create(ELVIS)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // target multiline statement
        val operationReference =
            node.treeParent
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
            operationReference.treePrev
                .takeIf { it.isWhiteSpaceWithNewline() }
                ?: return
            emit(node.startOffset, Messages[MSG_UNEXPECTED], false)
            return
        }
        operationReference.treePrev
            .takeIf { it.isWhiteSpaceWithoutNewline() }
            ?: return
        emit(node.startOffset, Messages[MSG_MISSING], false)
    }

    internal companion object {
        const val MSG_MISSING = "elvis.wrapping.missing"
        const val MSG_UNEXPECTED = "elvis.wrapping.unexpected"
    }
}
