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

/** [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#case-line-spacing) */
public class CaseLineSpacingRule : RulebookRule(ID) {
    override val tokens: TokenSet = TokenSet.create(WHEN_ENTRY)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // skip last branch
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
            emit(whitespace.endOffset, Messages[MSG_MISSING], false)
            return
        }
        whitespace
            .takeIf { it.isWhitespaceMultiline() }
            ?: return
        emit(whitespace.endOffset, Messages[MSG_UNEXPECTED], false)
    }

    internal companion object {
        val ID = RuleId("${RulebookRuleSet.ID.value}:case-line-spacing")

        const val MSG_MISSING = "case.line.spacing.missing"
        const val MSG_UNEXPECTED = "case.line.spacing.unexpected"
    }
}
