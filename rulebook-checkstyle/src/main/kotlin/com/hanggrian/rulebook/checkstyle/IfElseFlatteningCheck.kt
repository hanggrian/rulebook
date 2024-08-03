package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.hanggrian.rulebook.checkstyle.internals.children
import com.hanggrian.rulebook.checkstyle.internals.contains
import com.hanggrian.rulebook.checkstyle.internals.isMultiline
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_ELSE
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_IF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.RCURLY
import com.puppycrawl.tools.checkstyle.api.TokenTypes.SEMI
import com.puppycrawl.tools.checkstyle.api.TokenTypes.SINGLE_LINE_COMMENT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.SLIST

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#if-else-flattening)
 */
public class IfElseFlatteningCheck : Check() {
    override fun getRequiredTokens(): IntArray = intArrayOf(SLIST)

    override fun isCommentNodesRequired(): Boolean = true

    override fun visitToken(node: DetailAST) {
        // skip recursive if-else
        node.parent
            ?.takeUnless { it.type == LITERAL_IF || it.type == LITERAL_ELSE }
            ?: return

        // get last if
        var `if`: DetailAST? = null
        for (child in node.children.asIterable().reversed()) {
            when (child.type) {
                LITERAL_IF -> {
                    `if` = child
                    break
                }
                SEMI, RCURLY, SINGLE_LINE_COMMENT -> continue
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
            .takeIf { it.hasMultipleLines() }
            ?: return
        log(`if`, Messages[MSG_INVERT])
    }

    internal companion object {
        const val MSG_INVERT = "if.else.flattening.invert"
        const val MSG_LIFT = "if.else.flattening.lift"

        private fun DetailAST.hasMultipleLines() =
            findFirstToken(SLIST)
                ?.children
                ?.filter { it.type != RCURLY && it.type != SEMI }
                ?.let { it.singleOrNull()?.isMultiline() ?: (it.count() > 1) }
                ?: false
    }
}
