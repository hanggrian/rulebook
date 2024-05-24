package com.hendraanggrian.rulebook.checkstyle

import com.hendraanggrian.rulebook.checkstyle.internals.Messages
import com.hendraanggrian.rulebook.checkstyle.internals.contains
import com.hendraanggrian.rulebook.checkstyle.internals.joinText
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.DOT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.IMPORT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.METHOD_CALL
import com.puppycrawl.tools.checkstyle.api.TokenTypes.SEMI
import com.puppycrawl.tools.checkstyle.api.TokenTypes.TYPE

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#qualifier-consistency)
 */
public class QualifierConsistencyCheck : RulebookCheck() {
    private val importPaths = mutableSetOf<String>()
    private val targetNodes = mutableSetOf<DetailAST>()

    override fun getRequiredTokens(): IntArray = intArrayOf(IMPORT, TYPE, METHOD_CALL)

    override fun isCommentNodesRequired(): Boolean = true

    override fun visitToken(node: DetailAST) {
        // first line of filter
        when (node.type) {
            IMPORT ->
                // keep import list
                importPaths += node.joinText(".", SEMI)
            TYPE ->
                // keep expressions
                targetNodes += node
            METHOD_CALL -> {
                // only consider top expression
                val dot =
                    node.takeUnless { DOT !in it }
                        ?.firstChild
                        ?.takeUnless { DOT !in it }
                        ?.firstChild
                        ?: return

                // keep expressions
                targetNodes += dot
            }
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
