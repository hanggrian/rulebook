package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.hanggrian.rulebook.checkstyle.internals.joinText
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.DOT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.IMPORT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.METHOD_CALL
import com.puppycrawl.tools.checkstyle.api.TokenTypes.SEMI
import com.puppycrawl.tools.checkstyle.api.TokenTypes.TYPE

/** [See detail](https://hanggrian.github.io/rulebook/rules/all/#redundant-qualifier) */
public class RedundantQualifierCheck : RulebookAstCheck() {
    private val importPaths = mutableSetOf<String>()
    private val targetNodes = mutableSetOf<DetailAST>()

    override fun getRequiredTokens(): IntArray = intArrayOf(IMPORT, TYPE, METHOD_CALL)

    override fun visitToken(node: DetailAST) {
        // keep import list and expressions
        if (node.type == IMPORT) {
            importPaths += node.joinText(".", SEMI)
            return
        }
        when (node.type) {
            // keep class qualifier
            TYPE -> targetNodes += node

            // keep class qualifier and calling method
            else -> {
                val dot =
                    node
                        .findFirstToken(DOT)
                        ?: return
                targetNodes += dot
                targetNodes += dot.findFirstToken(DOT) ?: return
            }
        }
    }

    override fun finishTree(node: DetailAST): Unit =
        // checks for violation
        targetNodes
            .filter { it.joinText(".") in importPaths }
            .forEach { log(it, Messages[MSG]) }

    internal companion object {
        const val MSG = "redundant.qualifier"
    }
}
