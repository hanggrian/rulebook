package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.hanggrian.rulebook.checkstyle.internals.children
import com.hanggrian.rulebook.checkstyle.internals.isComment
import com.hanggrian.rulebook.checkstyle.internals.isMultiline
import com.hanggrian.rulebook.checkstyle.internals.maxLineNo
import com.hanggrian.rulebook.checkstyle.internals.minLineNo
import com.hanggrian.rulebook.checkstyle.internals.nextSibling
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
            node.children.any { it.isComment() }
        ) {
            caseGroup
                .takeIf { it.minLineNo != slist.maxLineNo + 2 }
                ?: return
            log(slist.lastChild, Messages[MSG_MISSING])
            return
        }
        caseGroup
            .takeIf { it.minLineNo != slist.maxLineNo + 1 }
            ?: return
        log(slist.lastChild, Messages[MSG_UNEXPECTED])
    }

    private companion object {
        const val MSG_MISSING = "case.separator.missing"
        const val MSG_UNEXPECTED = "case.separator.unexpected"
    }
}
