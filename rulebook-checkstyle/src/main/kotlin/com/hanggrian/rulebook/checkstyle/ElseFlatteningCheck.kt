package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.hanggrian.rulebook.checkstyle.internals.contains
import com.hanggrian.rulebook.checkstyle.internals.lastIf
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_ELSE
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_IF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_RETURN
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_THROW
import com.puppycrawl.tools.checkstyle.api.TokenTypes.SLIST

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#else-flattening)
 */
public class ElseFlatteningCheck : Check() {
    override fun getRequiredTokens(): IntArray = intArrayOf(SLIST)

    override fun isCommentNodesRequired(): Boolean = true

    override fun visitToken(node: DetailAST) {
        // checks for violation
        val `if` = node.lastIf ?: return
        val `else` = `if`.findFirstToken(LITERAL_ELSE) ?: return
        `if`
            .takeIf { LITERAL_IF !in `else` }
            ?.findFirstToken(SLIST)
            ?.takeIf { LITERAL_RETURN in it || LITERAL_THROW in it }
            ?: return
        log(`else`, Messages[MSG])
    }

    internal companion object {
        const val MSG = "else.flattening"
    }
}
