package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.Messages
import com.hanggrian.rulebook.ktlint.RulebookRuleSet
import com.hanggrian.rulebook.ktlint.endOffset
import com.hanggrian.rulebook.ktlint.siblingsUntil
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_LEADING_ASTERISK
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_TAG
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/** [See detail](https://hanggrian.github.io/rulebook/rules/#block-tag-indentation) */
public class BlockTagIndentationRule : RulebookRule(ID) {
    override val tokens: TokenSet = TokenSet.create(KDOC_LEADING_ASTERISK)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // find leading whitespace
        val text =
            node
                .takeIf { it.treeParent.elementType == KDOC_TAG }
                ?.siblingsUntil(KDOC_LEADING_ASTERISK)
                ?.joinToString("") { it.text }
                ?.takeIf { it.firstOrNull()?.isWhitespace() ?: false }
                ?.drop(1)
                ?: return

        // checks for violation
        text
            .takeIf {
                it.isNotEmpty() &&
                    it.first().isWhitespace() &&
                    !it.startsWith("    ")
            } ?: return
        emit(node.endOffset, Messages[MSG], false)
    }

    public companion object {
        public val ID: RuleId = RuleId("${RulebookRuleSet.ID.value}:block-tag-indentation")

        private const val MSG = "block.tag.indentation"
    }
}
