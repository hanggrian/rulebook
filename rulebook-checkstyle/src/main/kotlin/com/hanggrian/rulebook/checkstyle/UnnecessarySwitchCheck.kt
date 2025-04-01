package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.hanggrian.rulebook.checkstyle.internals.children
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.CASE_GROUP
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_CASE
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_SWITCH

/** [See detail](https://hanggrian.github.io/rulebook/rules/#unnecessary-switch) */
public class UnnecessarySwitchCheck : RulebookAstCheck() {
    override fun getRequiredTokens(): IntArray = intArrayOf(LITERAL_SWITCH)

    override fun visitToken(node: DetailAST) {
        // target single entry
        val caseGroup =
            node
                .children
                .singleOrNull { it.type == CASE_GROUP }
                ?: return

        // checks for violation
        caseGroup
            .children
            .takeUnless { cases2 -> cases2.count { it.type == LITERAL_CASE } > 1 }
            ?: return
        log(node, Messages[MSG])
    }

    internal companion object {
        const val MSG = "unnecessary.switch"
    }
}
