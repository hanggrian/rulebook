package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Messages
import com.hanggrian.rulebook.ktlint.internals.contains
import com.hanggrian.rulebook.ktlint.internals.hasReturnOrThrow
import com.hanggrian.rulebook.ktlint.internals.isComment
import com.hanggrian.rulebook.ktlint.internals.isMultiline
import com.pinterest.ktlint.rule.engine.core.api.ElementType.BLOCK
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS_INITIALIZER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.ELSE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.ELSE_KEYWORD
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IF
import com.pinterest.ktlint.rule.engine.core.api.ElementType.LBRACE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.RBRACE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.RETURN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.THEN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHITE_SPACE
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.children
import com.pinterest.ktlint.rule.engine.core.api.isWhiteSpace
import com.pinterest.ktlint.rule.engine.core.api.parent
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/** [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#if-else-flattening) */
public class IfElseFlatteningRule : RulebookRule(ID) {
    override val tokens: TokenSet = TokenSet.create(BLOCK)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // skip init block
        node
            .takeIf { n -> n.parent { it.elementType == CLASS_INITIALIZER } == null }
            ?: return

        // get last if
        var `if`: ASTNode? = null
        for (child in node.children().asIterable().reversed()) {
            when {
                child.elementType == IF -> {
                    `if` = child
                    break
                }

                child.elementType == RETURN -> {
                    `if` = child.findChildByType(IF) ?: return
                    break
                }

                child.elementType == WHITE_SPACE ||
                    child.elementType == RBRACE ||
                    child.isComment() -> continue

                else -> return
            }
        }
        `if` ?: return

        // checks for violation
        val `else` = `if`.findChildByType(ELSE)
        if (`else` != null) {
            `else`
                .takeUnless { IF in it }
                ?.takeIf { it.hasMultipleLines() }
                ?: return
            emit(`if`.findChildByType(ELSE_KEYWORD)!!.startOffset, Messages[MSG_LIFT], false)
            return
        }
        `if`
            .takeUnless { it.hasReturnOrThrow() }
            ?.findChildByType(THEN)
            ?.takeIf { it.hasMultipleLines() }
            ?: return
        emit(`if`.startOffset, Messages[MSG_INVERT], false)
    }

    internal companion object {
        val ID = RuleId("${RulebookRuleSet.ID.value}:if-else-flattening")

        const val MSG_INVERT = "if.else.flattening.invert"
        const val MSG_LIFT = "if.else.flattening.lift"

        private fun ASTNode.hasMultipleLines() =
            findChildByType(BLOCK)
                ?.children()
                .orEmpty()
                .filterNot {
                    it.elementType == LBRACE || it.elementType == RBRACE || it.isWhiteSpace()
                }.let { it.singleOrNull()?.isMultiline() ?: (it.count() > 1) }
    }
}
