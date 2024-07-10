package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.SINGLE_LINE_COMMENT
import kotlin.math.min

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#file-initial-line-trimming)
 */
public class FileInitialLineTrimmingCheck : Check() {
    private var minIndex = -1

    override fun getRequiredTokens(): IntArray = intArrayOf(SINGLE_LINE_COMMENT)

    override fun isCommentNodesRequired(): Boolean = true

    override fun visitToken(node: DetailAST) {
        minIndex = min(minIndex, node.lineNo)
    }

    override fun finishTree(node: DetailAST) {
        // checks for violation
        node
            .takeUnless { minIndex == 1 }
            ?.takeIf { it.lineNo > 1 } ?: return
        log(node, Messages[MSG])
    }

    internal companion object {
        const val MSG = "file.initial.line.trimming"
    }
}
