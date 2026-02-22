package com.hanggrian.rulebook.checkstyle.checks

import com.hanggrian.rulebook.checkstyle.Messages
import com.hanggrian.rulebook.checkstyle.children
import com.hanggrian.rulebook.checkstyle.isComment
import com.hanggrian.rulebook.checkstyle.isMultiline
import com.hanggrian.rulebook.checkstyle.maxLineNo
import com.hanggrian.rulebook.checkstyle.minLineNo
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.CASE_GROUP
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_CASE
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_SWITCH
import com.puppycrawl.tools.checkstyle.api.TokenTypes.SLIST

/** [See detail](https://hanggrian.github.io/rulebook/rules/#case-separator) */
public class CaseSeparatorCheck : RulebookAstCheck() {
    override fun getRequiredTokens(): IntArray = intArrayOf(LITERAL_SWITCH)

    override fun isCommentNodesRequired(): Boolean = true

    override fun visitToken(node: DetailAST) {
        // collect cases
        val caseGroups =
            node
                .children()
                .filter { it.type == CASE_GROUP }
                .toList()
                .takeUnless { it.isEmpty() }
                ?: return

        // checks for violation
        val hasMultiline =
            caseGroups.any { caseGroup ->
                val children = caseGroup.children()
                caseGroup.findFirstToken(SLIST)?.isMultiline() == true ||
                    children.any { it.isComment() } ||
                    children.count { it.type == LITERAL_CASE } > 1
            }
        for ((i, caseGroup) in caseGroups.withIndex()) {
            val lastSlist =
                caseGroups
                    .getOrNull(i - 1)
                    ?.findFirstToken(SLIST)
                    ?: continue
            when {
                hasMultiline ->
                    lastSlist
                        .takeIf { caseGroup.minLineNo != it.maxLineNo + 2 }
                        ?.run { log(lastChild, Messages[MSG_MISSING]) }

                caseGroup.minLineNo != lastSlist.maxLineNo + 1 ->
                    log(lastSlist.lastChild, Messages[MSG_UNEXPECTED])
            }
        }
    }

    private companion object {
        const val MSG_MISSING = "case.separator.missing"
        const val MSG_UNEXPECTED = "case.separator.unexpected"
    }
}
