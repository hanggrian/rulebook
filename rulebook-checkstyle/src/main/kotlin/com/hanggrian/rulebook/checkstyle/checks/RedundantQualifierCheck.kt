package com.hanggrian.rulebook.checkstyle.checks

import com.hanggrian.rulebook.checkstyle.Messages
import com.hanggrian.rulebook.checkstyle.children
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.DOT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.IDENT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.IMPORT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.METHOD_CALL
import com.puppycrawl.tools.checkstyle.api.TokenTypes.TYPE

/** [See detail](https://hanggrian.github.io/rulebook/rules/#redundant-qualifier) */
public class RedundantQualifierCheck : RulebookAstCheck() {
    private val importNodes = mutableSetOf<DetailAST>()
    private val targetNodes = mutableSetOf<DetailAST>()

    override fun getRequiredTokens(): IntArray = intArrayOf(IMPORT, TYPE, METHOD_CALL)

    override fun visitToken(node: DetailAST) {
        // keep import list
        if (node.type == IMPORT) {
            node.findFirstToken(DOT)?.let { importNodes += it }
            return
        }

        // checks for violation
        val dot = node.findFirstToken(DOT) ?: return
        process(dot)
        if (node.type == METHOD_CALL) {
            process(dot.findFirstToken(DOT))
        }
    }

    private fun process(dot: DetailAST?) {
        dot
            ?.takeIf { n -> importNodes.any { n.isDotEquals(it) } && targetNodes.add(n) }
            ?: return
        log(dot, Messages[MSG])
    }

    private companion object {
        const val MSG = "redundant.qualifier"

        fun DetailAST.isDotEquals(other: DetailAST): Boolean {
            var dot1: DetailAST? = this
            var dot2: DetailAST? = other
            while (dot1 != null && dot2 != null) {
                if (!dot1.dotIdentTexts.containsAll(dot2.dotIdentTexts)) {
                    return false
                }
                dot1 = dot1.findFirstToken(DOT)
                dot2 = dot2.findFirstToken(DOT)
            }
            return dot1 == null && dot2 == null
        }

        val DetailAST.dotIdentTexts: List<String>
            get() =
                children()
                    .filter { it.type == IDENT }
                    .map { it.text }
                    .toList()
    }
}
