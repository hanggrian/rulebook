package com.hanggrian.rulebook.checkstyle.checks

import com.hanggrian.rulebook.checkstyle.Messages
import com.hanggrian.rulebook.checkstyle.children
import com.hanggrian.rulebook.checkstyle.isComment
import com.hanggrian.rulebook.checkstyle.isMultiline
import com.hanggrian.rulebook.checkstyle.maxLineNo
import com.hanggrian.rulebook.checkstyle.minLineNo
import com.hanggrian.rulebook.checkstyle.nextSibling
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.CASE_GROUP
import com.puppycrawl.tools.checkstyle.api.TokenTypes.SLIST

/** [See detail](https://hanggrian.github.io/rulebook/rules/#case-separator) */
public class CaseSeparatorCheck : RulebookAstCheck() {
    override fun getRequiredTokens(): IntArray = intArrayOf(CASE_GROUP)

    override fun isCommentNodesRequired(): Boolean = true

    override fun visitToken(node: DetailAST) {
        // targeting case, skip last branch
        val caseGroup =
            node
                .nextSibling { it.type == CASE_GROUP }
                ?: return

        // checks for violation
        val slist = node.findFirstToken(SLIST) ?: return
        if (slist.isMultiline() ||
            node.children().any { it.isComment() }
        ) {
            caseGroup
                .minLineNo
                .takeIf { it != slist.maxLineNo + 2 }
                ?: return
            log(slist.lastChild, Messages[MSG_MISSING])
            return
        }
        caseGroup
            .minLineNo
            .takeIf { it != slist.maxLineNo + 1 }
            ?: return
        log(slist.lastChild, Messages[MSG_UNEXPECTED])
    }

    private companion object {
        const val MSG_MISSING = "case.separator.missing"
        const val MSG_UNEXPECTED = "case.separator.unexpected"
    }
}
