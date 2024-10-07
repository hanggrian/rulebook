package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Messages
import com.hanggrian.rulebook.ktlint.internals.endOffset
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FLOAT_LITERAL
import com.pinterest.ktlint.rule.engine.core.api.ElementType.INTEGER_LITERAL
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#float-literal-tagging)
 */
public class FloatLiteralTaggingRule : Rule("float-literal-tagging") {
    override val tokens: TokenSet = TokenSet.create(FLOAT_LITERAL, INTEGER_LITERAL)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // find trailing tag
        val character =
            node.text
                .last()
                .takeIf { it.lowercaseChar() == 'f' }
                ?: return

        // checks for violation
        if (node.elementType == INTEGER_LITERAL) {
            character
                .takeIf { c -> c.isLowerCase() }
                ?: return
            emit(node.endOffset, Messages[MSG_HEX], false)
        }
        character
            .takeIf { it.isUpperCase() }
            ?: return
        emit(node.endOffset, Messages[MSG_NUM], false)
    }

    internal companion object {
        const val MSG_NUM = "float.literal.tagging.num"
        const val MSG_HEX = "float.literal.tagging.hex"
    }
}
