package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.Messages
import com.hanggrian.rulebook.ktlint.RulebookRuleSet
import com.hanggrian.rulebook.ktlint.contains
import com.hanggrian.rulebook.ktlint.isComment
import com.pinterest.ktlint.rule.engine.core.api.ElementType.BLOCK
import com.pinterest.ktlint.rule.engine.core.api.ElementType.BOOLEAN_CONSTANT
import com.pinterest.ktlint.rule.engine.core.api.ElementType.ELSE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IF
import com.pinterest.ktlint.rule.engine.core.api.ElementType.LBRACE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.RBRACE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.RETURN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.THEN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHITE_SPACE
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.children20
import com.pinterest.ktlint.rule.engine.core.api.nextSibling
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet.create

/** [See detail](https://hanggrian.github.io/rulebook/rules/#redundant-if) */
public class RedundantIfRule : RulebookRule(ID) {
    override val tokens: TokenSet = create(IF)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // checks for violation
        node
            .findChildByType(THEN)
            ?.takeIf { it.isThenConstant() }
            ?: return
        node
            .findChildByType(ELSE)
            ?.takeIf { IF !in it && it.isThenConstant() }
            ?.run {
                emit(node.startOffset, Messages[MSG], false)
                return
            }
        node
            .nextSibling {
                it.elementType !== WHITE_SPACE &&
                    it.elementType !== RBRACE &&
                    !it.isComment()
            }?.takeIf { it.isStatementConstant() }
            ?: return
        emit(node.startOffset, Messages[MSG], false)
    }

    public companion object {
        public val ID: RuleId = RuleId("${RulebookRuleSet.ID.value}:redundant-if")
        private const val MSG = "redundant.if"

        private fun ASTNode.isThenConstant(): Boolean =
            (findChildByType(BLOCK) ?: this)
                .children20
                .singleOrNull {
                    it.elementType !== WHITE_SPACE &&
                        it.elementType !== LBRACE &&
                        it.elementType !== RBRACE &&
                        !it.isComment()
                }?.isStatementConstant()
                ?: false

        private fun ASTNode.isStatementConstant() =
            when (elementType) {
                BOOLEAN_CONSTANT -> true
                RETURN -> BOOLEAN_CONSTANT in this
                else -> false
            }
    }
}
