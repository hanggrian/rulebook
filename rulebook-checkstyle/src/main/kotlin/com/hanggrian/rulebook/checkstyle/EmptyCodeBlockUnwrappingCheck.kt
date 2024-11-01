package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.hanggrian.rulebook.checkstyle.internals.isLeaf
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LCURLY
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_CATCH
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_ELSE
import com.puppycrawl.tools.checkstyle.api.TokenTypes.OBJBLOCK
import com.puppycrawl.tools.checkstyle.api.TokenTypes.RCURLY
import com.puppycrawl.tools.checkstyle.api.TokenTypes.SLIST
import com.puppycrawl.tools.checkstyle.grammar.java.JavaLanguageParser.LITERAL_IF
import com.puppycrawl.tools.checkstyle.grammar.java.JavaLanguageParser.LITERAL_TRY

/** [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#empty-code-block-unwrapping) */
public class EmptyCodeBlockUnwrappingCheck : RulebookCheck() {
    override fun getRequiredTokens(): IntArray = intArrayOf(OBJBLOCK, SLIST)

    override fun isCommentNodesRequired(): Boolean = true

    override fun visitToken(node: DetailAST) {
        // skip control flows that can have multi-blocks
        node
            .parent
            .type
            .takeUnless {
                it == LITERAL_TRY ||
                    it == LITERAL_CATCH ||
                    it == LITERAL_IF ||
                    it == LITERAL_ELSE
            } ?: return

        // obtain corresponding braces
        val (lcurcly, rcurcly) =
            when (node.type) {
                OBJBLOCK -> {
                    // skip non-empty content
                    node
                        .takeUnless { it.isLeaf() || it.childCount > 2 }
                        ?: return

                    // class block have left and right braces
                    val lcurly = node.firstChild.takeIf { it.type == LCURLY } ?: return
                    val rcurly = node.lastChild.takeIf { it.type == RCURLY } ?: return
                    lcurly to rcurly
                }
                else -> {
                    // skip non-empty content
                    node
                        .takeUnless { it.isLeaf() || it.childCount > 1 }
                        ?: return

                    // function block only have right brace
                    node
                        .lastChild
                        .takeIf { it.type == RCURLY }
                        ?.let { node to it }
                        ?: return
                }
            }

        // checks for violation
        lcurcly
            .takeUnless {
                it.lineNo == rcurcly.lineNo &&
                    lcurcly.columnNo + 1 == rcurcly.columnNo
            } ?: return
        log(lcurcly, Messages[MSG])
    }

    internal companion object {
        const val MSG = "empty.code.block.unwrapping"
    }
}
