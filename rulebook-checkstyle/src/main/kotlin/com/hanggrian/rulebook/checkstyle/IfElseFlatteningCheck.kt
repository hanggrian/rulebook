package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.hanggrian.rulebook.checkstyle.internals.children
import com.hanggrian.rulebook.checkstyle.internals.contains
import com.hanggrian.rulebook.checkstyle.internals.hasReturnOrThrow
import com.hanggrian.rulebook.checkstyle.internals.isComment
import com.hanggrian.rulebook.checkstyle.internals.isMultiline
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_ELSE
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_IF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.RCURLY
import com.puppycrawl.tools.checkstyle.api.TokenTypes.SEMI
import com.puppycrawl.tools.checkstyle.api.TokenTypes.SLIST

/** [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#if-else-flattening) */
public class IfElseFlatteningCheck : RulebookCheck() {
    override fun getRequiredTokens(): IntArray = intArrayOf(SLIST)

    override fun isCommentNodesRequired(): Boolean = true

    override fun visitToken(node: DetailAST) {
        // get last if
        var `if`: DetailAST? = null
        for (child in node.children.asIterable().reversed()) {
            when {
                child.type == LITERAL_IF -> {
                    `if` = child
                    break
                }

                child.type == SEMI ||
                    child.type == RCURLY ||
                    child.isComment() -> continue

                else -> return
            }
        }
        `if` ?: return

        // checks for violation
        val `else` = `if`.findFirstToken(LITERAL_ELSE)
        if (`else` != null) {
            `else`
                .takeUnless { LITERAL_IF in it }
                ?.takeIf { it.hasMultipleLines() }
                ?: return
            log(`else`, Messages[MSG_LIFT])
            return
        }
        `if`
            .takeUnless { it.hasReturnOrThrow() }
            ?.takeIf { it.hasMultipleLines() }
            ?: return
        log(`if`, Messages[MSG_INVERT])
    }

    internal companion object {
        const val MSG_INVERT = "if.else.flattening.invert"
        const val MSG_LIFT = "if.else.flattening.lift"

        private fun DetailAST.hasMultipleLines() =
            findFirstToken(SLIST)
                ?.children
                ?.filterNot { it.type == RCURLY || it.type == SEMI }
                ?.let { it.singleOrNull()?.isMultiline() ?: (it.count() > 1) }
                ?: false
    }
}
