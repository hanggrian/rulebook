package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.hanggrian.rulebook.checkstyle.internals.children
import com.hanggrian.rulebook.checkstyle.internals.contains
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.CASE_GROUP
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_CONTINUE
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_DEFAULT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_RETURN
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_SWITCH
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_THROW
import com.puppycrawl.tools.checkstyle.api.TokenTypes.SLIST

/** [See detail](https://hanggrian.github.io/rulebook/rules/all/#redundant-default) */
public class RedundantDefaultCheck : RulebookAstCheck() {
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
            .takeIf { cases2 ->
                cases2.all { c ->
                    c.findFirstToken(SLIST).let {
                        LITERAL_RETURN in it ||
                            LITERAL_THROW in it ||
                            LITERAL_CONTINUE in it
                    }
                }
            } ?: return
        log(default, Messages[MSG])
    }

    internal companion object {
        const val MSG = "redundant.default"
    }
}
