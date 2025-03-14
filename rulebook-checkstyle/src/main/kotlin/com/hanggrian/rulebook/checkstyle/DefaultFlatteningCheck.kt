package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.hanggrian.rulebook.checkstyle.internals.children
import com.hanggrian.rulebook.checkstyle.internals.contains
import com.hanggrian.rulebook.checkstyle.internals.hasReturnOrThrow
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.CASE_GROUP
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_DEFAULT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_SWITCH

/** [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#default-flattening) */
public class DefaultFlatteningCheck : RulebookCheck() {
    override fun getRequiredTokens(): IntArray = intArrayOf(LITERAL_SWITCH)

    override fun isCommentNodesRequired(): Boolean = true

    override fun visitToken(node: DetailAST) {
        // skip no default
        val caseGroups =
            node
                .children
                .filter { it.type == CASE_GROUP }
        val default =
            caseGroups
                .lastOrNull()
                ?.takeIf { LITERAL_DEFAULT in it }
                ?: return

        // checks for violation
        caseGroups
            .toList()
            .dropLast(1)
            .takeIf { cases2 -> cases2.all { it.hasReturnOrThrow() } }
            ?: return
        log(default, Messages[MSG])
    }

    internal companion object {
        const val MSG = "default.flattening"
    }
}
