package com.hanggrian.rulebook.checkstyle.checks

import com.hanggrian.rulebook.checkstyle.Messages
import com.hanggrian.rulebook.checkstyle.children
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.CASE_GROUP
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_CASE
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_SWITCH

/** [See detail](https://hanggrian.github.io/rulebook/rules/#lonely-case) */
public class LonelyCaseCheck : RulebookAstCheck() {
    override fun getRequiredTokens(): IntArray = intArrayOf(LITERAL_SWITCH)

    override fun visitToken(node: DetailAST) {
        // skip multiple branches
        val caseGroup =
            node
                .children()
                .singleOrNull { it.type == CASE_GROUP }
                ?: return

        // checks for violation
        caseGroup
            .children()
            .takeUnless { cases2 -> cases2.count { it.type == LITERAL_CASE } > 1 }
            ?: return
        log(node, Messages[MSG])
    }

    private companion object {
        const val MSG = "lonely.case"
    }
}
