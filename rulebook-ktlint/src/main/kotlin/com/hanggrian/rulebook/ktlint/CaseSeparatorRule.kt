package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Messages
import com.hanggrian.rulebook.ktlint.internals.endOffset
import com.hanggrian.rulebook.ktlint.internals.isComment
import com.hanggrian.rulebook.ktlint.internals.isMultiline
import com.hanggrian.rulebook.ktlint.internals.isWhitespaceMultiline
import com.hanggrian.rulebook.ktlint.internals.isWhitespaceSingleLine
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHEN_ENTRY
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.nextSibling
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/** [See detail](https://hanggrian.github.io/rulebook/rules/all/#case-separator) */
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

    internal companion object {
        val ID = RuleId("${RulebookRuleSet.ID.value}:case-separator")

        const val MSG_MISSING = "case.separator.missing"
        const val MSG_UNEXPECTED = "case.separator.unexpected"
    }
}
