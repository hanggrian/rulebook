package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.hanggrian.rulebook.checkstyle.internals.contains
import com.hanggrian.rulebook.checkstyle.internals.joinText
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.DOT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.IMPORT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.METHOD_CALL
import com.puppycrawl.tools.checkstyle.api.TokenTypes.SEMI
import com.puppycrawl.tools.checkstyle.api.TokenTypes.TYPE

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#qualifier-consistency)
 */
public class QualifierConsistencyCheck : Check() {
    private val importPaths = mutableSetOf<String>()
    private val targetNodes = mutableSetOf<DetailAST>()

    override fun getRequiredTokens(): IntArray = intArrayOf(IMPORT, TYPE, METHOD_CALL)

    override fun visitToken(node: DetailAST) {
        // keep import list and expressions
        if (node.type == IMPORT) {
            importPaths += node.joinText(".", SEMI)
            return
        }
        targetNodes +=
            when (node.type) {
                TYPE -> node
                else ->
                    node
                        .takeIf { DOT in it }
                        ?.firstChild
                        ?.takeIf { DOT in it }
                        ?.firstChild
                        ?: return
            }
    }

    override fun finishTree(node: DetailAST) {
        // checks for violation
        for (targetNode in targetNodes) {
            if (targetNode.joinText(".") !in importPaths) {
                continue
            }
            log(targetNode, Messages[MSG])
        }
    }

    internal companion object {
        const val MSG = "qualifier.consistency"
    }
}
