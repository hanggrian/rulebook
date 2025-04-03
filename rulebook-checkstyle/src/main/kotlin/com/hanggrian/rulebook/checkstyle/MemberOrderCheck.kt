package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.hanggrian.rulebook.checkstyle.internals.children
import com.hanggrian.rulebook.checkstyle.internals.hasModifier
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
        var lastChildType: Int? = null
        node
            .children
            .filter {
                it.type == VARIABLE_DEF ||
                    it.type == CTOR_DEF ||
                    it.type == COMPACT_CTOR_DEF ||
                    it.type == METHOD_DEF
            }.filterNot { it.hasModifier(LITERAL_STATIC) }
            .forEach {
                // checks for violation
                if (MEMBER_POSITIONS.getOrDefault(lastChildType, -1) >
                    MEMBER_POSITIONS[it.type]!!
                ) {
                    log(
                        it,
                        Messages.get(
                            MSG,
                            MEMBER_ARGUMENTS[it.type]!!,
                            MEMBER_ARGUMENTS[lastChildType]!!,
                        ),
                    )
                }

                lastChildType = it.type
            }
    }

    private companion object {
        const val MSG = "member.order"

        val MEMBER_POSITIONS =
            mapOf(
                VARIABLE_DEF to 0,
                CTOR_DEF to 1,
                COMPACT_CTOR_DEF to 1,
                METHOD_DEF to 2,
            )

        val MEMBER_ARGUMENTS =
            mapOf(
                VARIABLE_DEF to "property",
                CTOR_DEF to "constructor",
                COMPACT_CTOR_DEF to "constructor",
                METHOD_DEF to "function",
            )
    }
}
