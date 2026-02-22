package com.hanggrian.rulebook.checkstyle.checks

import com.hanggrian.rulebook.checkstyle.Messages
import com.hanggrian.rulebook.checkstyle.maxLineNo
import com.hanggrian.rulebook.checkstyle.minLineNo
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.GENERIC_END
import com.puppycrawl.tools.checkstyle.api.TokenTypes.GENERIC_START
import com.puppycrawl.tools.checkstyle.api.TokenTypes.TYPE_ARGUMENTS
import com.puppycrawl.tools.checkstyle.api.TokenTypes.TYPE_PARAMETERS

/** [See detail](https://hanggrian.github.io/rulebook/rules/#tags-trim) */
public class TagsTrimCheck : RulebookAstCheck() {
    override fun getRequiredTokens(): IntArray = intArrayOf(TYPE_ARGUMENTS, TYPE_PARAMETERS)

    override fun isCommentNodesRequired(): Boolean = true

    override fun visitToken(node: DetailAST) {
        // right and left tags are persistent
        val genericStart = node.findFirstToken(GENERIC_START) ?: return
        val genericEnd = node.findFirstToken(GENERIC_END) ?: return
        val genericStartSibling = genericStart.nextSibling ?: return
        val genericEndSibling = genericEnd.previousSibling ?: return

        // checks for violation
        genericStartSibling
            .minLineNo
            .takeIf { it - genericStart.lineNo > 1 }
            ?.let { log(it - 1, Messages[MSG_FIRST]) }
        genericEndSibling
            .maxLineNo
            .takeIf { genericEnd.lineNo - it > 1 }
            ?.let { log(it + 1, Messages[MSG_LAST]) }
    }

    private companion object {
        const val MSG_FIRST = "tags.trim.first"
        const val MSG_LAST = "tags.trim.last"
    }
}
