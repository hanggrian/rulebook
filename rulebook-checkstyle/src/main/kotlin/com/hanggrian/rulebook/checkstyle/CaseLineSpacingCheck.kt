package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.hanggrian.rulebook.checkstyle.internals.isMultiline
import com.hanggrian.rulebook.checkstyle.internals.maxLineNo
import com.hanggrian.rulebook.checkstyle.internals.minLineNo
import com.hanggrian.rulebook.checkstyle.internals.nextSibling
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.CASE_GROUP
import com.puppycrawl.tools.checkstyle.api.TokenTypes.SLIST

/** [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#case-line-spacing) */
public class CaseLineSpacingCheck : RulebookCheck() {
    override fun getRequiredTokens(): IntArray = intArrayOf(CASE_GROUP)

    override fun visitToken(node: DetailAST) {
        // skip last branch
        val caseGroup = node.nextSibling { it.type == CASE_GROUP } ?: return

        // checks for violation
        val slist = node.findFirstToken(SLIST) ?: return
        if (slist.isMultiline()) {
            caseGroup
                .takeIf { it.minLineNo != slist.maxLineNo + 2 }
                ?: return
            log(node, Messages[MSG_MISSING])
            return
        }
        caseGroup
            .takeIf { it.minLineNo != slist.maxLineNo + 1 }
            ?: return
        log(node, Messages[MSG_UNEXPECTED])
    }

    internal companion object {
        const val MSG_MISSING = "case.line.spacing.missing"
        const val MSG_UNEXPECTED = "case.line.spacing.unexpected"
    }
}
