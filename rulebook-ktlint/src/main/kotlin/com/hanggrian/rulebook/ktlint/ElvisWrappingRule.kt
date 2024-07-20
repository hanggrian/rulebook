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
        val prevLastChild =
            operationReference
                .prevCodeSibling()
                ?.lastMostChild
                ?: return
        if (prevLastChild.elementType == RBRACE &&
            prevLastChild.treePrev.isWhiteSpaceWithNewline()
        ) {
            operationReference.treePrev
                .takeIf { it.isWhiteSpaceWithNewline() }
                ?: return
            emit(node.startOffset, Messages[MSG], false)
            return
        }
        operationReference.treePrev
            .takeIf { it.isWhiteSpaceWithoutNewline() }
            ?: return
        emit(node.startOffset, Messages[MSG], false)
    }

    internal companion object {
        const val MSG = "elvis.wrapping"
    }
}
