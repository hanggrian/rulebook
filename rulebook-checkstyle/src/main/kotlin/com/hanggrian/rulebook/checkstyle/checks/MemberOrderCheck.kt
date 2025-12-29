package com.hanggrian.rulebook.checkstyle.checks

import com.hanggrian.rulebook.checkstyle.Messages
import com.hanggrian.rulebook.checkstyle.children
import com.hanggrian.rulebook.checkstyle.hasModifier
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.COMPACT_CTOR_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.CTOR_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_STATIC
import com.puppycrawl.tools.checkstyle.api.TokenTypes.METHOD_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.OBJBLOCK
import com.puppycrawl.tools.checkstyle.api.TokenTypes.VARIABLE_DEF

/** [See detail](https://hanggrian.github.io/rulebook/rules/#member-order) */
public class MemberOrderCheck : RulebookAstCheck() {
    override fun getRequiredTokens(): IntArray = intArrayOf(OBJBLOCK)

    override fun visitToken(node: DetailAST) {
        // in Java, static members have specific keyword
        var lastChild: DetailAST? = null
        node
            .children()
            .filter {
                it.type == VARIABLE_DEF ||
                    it.type == CTOR_DEF ||
                    it.type == COMPACT_CTOR_DEF ||
                    it.type == METHOD_DEF
            }.forEach { child ->
                // checks for violation
                if ((lastChild?.memberPosition ?: -1) > child.memberPosition) {
                    log(
                        child,
                        Messages.get(MSG, child.memberArgument, lastChild!!.memberArgument),
                    )
                }

                lastChild = child
            }
    }

    private companion object {
        const val MSG = "member.order"

        private val DetailAST.memberPosition: Int
            get() =
                when (type) {
                    VARIABLE_DEF -> if (hasModifier(LITERAL_STATIC)) 4 else 1
                    CTOR_DEF, COMPACT_CTOR_DEF -> 2
                    METHOD_DEF -> if (hasModifier(LITERAL_STATIC)) 4 else 3
                    else -> -1
                }

        private val DetailAST.memberArgument: String
            get() =
                when (type) {
                    VARIABLE_DEF -> if (hasModifier(LITERAL_STATIC)) "static member" else "property"
                    CTOR_DEF, COMPACT_CTOR_DEF -> "constructor"
                    METHOD_DEF -> if (hasModifier(LITERAL_STATIC)) "static member" else "function"
                    else -> ""
                }
    }
}
