package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.Messages
import com.hanggrian.rulebook.ktlint.RulebookRuleSet
import com.hanggrian.rulebook.ktlint.endOffset
import com.hanggrian.rulebook.ktlint.isComment
import com.hanggrian.rulebook.ktlint.isMultiline
import com.hanggrian.rulebook.ktlint.isWhitespaceMultiline
import com.hanggrian.rulebook.ktlint.isWhitespaceSingleLine
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHEN_ENTRY
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.nextSibling
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/** [See detail](https://hanggrian.github.io/rulebook/rules/#case-separator) */
public class CaseSeparatorRule : RulebookRule(ID) {
    override val tokens: TokenSet = TokenSet.create(WHEN_ENTRY)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // targeting case, skip last branch
        val whitespace =
            node
                .takeUnless { n -> n.nextSibling { it.elementType == WHEN_ENTRY } == null }
                ?.treeNext
                ?: return

        // checks for violation
        if (node.isMultiline() ||
            node.treePrev?.let {
                it.isWhitespaceSingleLine() &&
                    it.treePrev?.isComment() == true
            } == true
        ) {
            whitespace
                .takeIf { it.isWhitespaceSingleLine() }
                ?: return
            emit(node.endOffset, Messages[MSG_MISSING], false)
            return
        }
        whitespace
            .takeIf { it.isWhitespaceMultiline() }
            ?: return
        emit(node.endOffset, Messages[MSG_UNEXPECTED], false)
    }

    public companion object {
        public val ID: RuleId = RuleId("${RulebookRuleSet.ID.value}:case-separator")

        private const val MSG_MISSING = "case.separator.missing"
        private const val MSG_UNEXPECTED = "case.separator.unexpected"
    }
}
