package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Messages
import com.hanggrian.rulebook.ktlint.internals.endOffset
import com.pinterest.ktlint.rule.engine.core.api.ElementType.BLOCK
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CATCH
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS_BODY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.ELSE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FUNCTION_LITERAL
import com.pinterest.ktlint.rule.engine.core.api.ElementType.LBRACE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.RBRACE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.THEN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.TRY
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.isLeaf
import com.pinterest.ktlint.rule.engine.core.api.isWhiteSpace
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet
import org.jetbrains.kotlin.psi.psiUtil.siblings

/** [See detail](https://hanggrian.github.io/rulebook/rules/#empty-code-block-join) */
public class EmptyCodeBlockJoinRule : RulebookRule(ID) {
    override val tokens: TokenSet = TokenSet.create(CLASS_BODY, BLOCK, FUNCTION_LITERAL)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // skip control flows that can have multi-blocks
        node
            .treeParent
            .elementType
            .takeUnless { it == TRY || it == CATCH || it == THEN || it == ELSE }
            ?: return

        // obtain corresponding braces
        node
            .takeUnless { it.isLeaf() }
            ?: return
        val lbrace =
            node
                .firstChildNode
                .takeIf { it.elementType == LBRACE }
                ?: return
        val rbrace =
            node
                .lastChildNode
                .takeIf { it.elementType == RBRACE }
                ?: return

        // checks for violation
        lbrace
            .siblings()
            .filter {
                when {
                    it.elementType == BLOCK -> !it.isLeaf()
                    else -> it !== rbrace
                }
            }.takeIf { nodes -> nodes.any() && nodes.all { it.isWhiteSpace() } }
            ?: return
        emit(lbrace.endOffset, Messages[MSG], false)
    }

    public companion object {
        public val ID: RuleId = RuleId("${RulebookRuleSet.ID.value}:empty-code-block-join")

        private const val MSG = "empty.code.block.join"
    }
}
