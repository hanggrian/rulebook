package com.hanggrian.rulebook.checkstyle.checks

import com.hanggrian.rulebook.checkstyle.Messages
import com.hanggrian.rulebook.checkstyle.children
import com.hanggrian.rulebook.checkstyle.contains
import com.hanggrian.rulebook.checkstyle.hasJumpStatement
import com.hanggrian.rulebook.checkstyle.isComment
import com.hanggrian.rulebook.checkstyle.isMultiline
import com.hanggrian.rulebook.checkstyle.parent
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_CATCH
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_ELSE
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_IF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_TRY
import com.puppycrawl.tools.checkstyle.api.TokenTypes.RCURLY
import com.puppycrawl.tools.checkstyle.api.TokenTypes.SEMI
import com.puppycrawl.tools.checkstyle.api.TokenTypes.SLIST

/** [See detail](https://hanggrian.github.io/rulebook/rules/#nested-if-else) */
public class NestedIfElseCheck : RulebookAstCheck() {
    override fun getRequiredTokens(): IntArray = intArrayOf(SLIST)

    override fun isCommentNodesRequired(): Boolean = true

    override fun visitToken(node: DetailAST) {
        // skip blocks without exit path
        val slist =
            node.takeUnless { it.parent.isTryCatch() }
                ?: node.parent { it.type == SLIST && !it.parent.isTryCatch() && LITERAL_TRY in it }
                ?: return
        slist
            .takeUnless {
                it.parent.type == LITERAL_IF ||
                    it.parent.type == LITERAL_ELSE
            } ?: return

        // get last if
        var `if`: DetailAST? = null
        for (child in node.children().asIterable().reversed()) {
            when {
                child.type == LITERAL_IF -> {
                    `if` = child
                    break
                }

                child.type == SEMI ||
                    child.type == RCURLY ||
                    child.isComment() -> continue
            }
            return
        }
        `if` ?: return

        // checks for violation
        val `else` = `if`.findFirstToken(LITERAL_ELSE)
        if (`else` != null) {
            `else`
                .takeIf { LITERAL_IF !in it && it.hasMultipleLines() }
                ?: return
            log(`else`, Messages[MSG_LIFT])
            return
        }
        `if`
            .takeIf { !it.hasJumpStatement() && it.hasMultipleLines() }
            ?: return
        log(`if`, Messages[MSG_INVERT])
    }

    private companion object {
        const val MSG_INVERT = "nested.if.else.invert"
        const val MSG_LIFT = "nested.if.else.lift"

        fun DetailAST.hasMultipleLines() =
            findFirstToken(SLIST)
                ?.children()
                .orEmpty()
                .filterNot { it.type == RCURLY || it.type == SEMI }
                .let { it.singleOrNull()?.isMultiline() ?: (it.count() > 1) }

        fun DetailAST.isTryCatch() = type == LITERAL_TRY || type == LITERAL_CATCH
    }
}
