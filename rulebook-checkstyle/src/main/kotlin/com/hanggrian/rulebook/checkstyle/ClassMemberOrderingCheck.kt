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

/** [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#class-member-ordering) */
public class ClassMemberOrderingCheck : RulebookCheck() {
    override fun getRequiredTokens(): IntArray = intArrayOf(OBJBLOCK)

    override fun visitToken(node: DetailAST) {
        var lastType = -1
        for (child in node
            .children
            .filter {
                it.type == VARIABLE_DEF ||
                    it.type == CTOR_DEF ||
                    it.type == COMPACT_CTOR_DEF ||
                    it.type == METHOD_DEF
            }) {
            // in Java, static members have specific keyword
            child
                .takeUnless { it.hasModifier(LITERAL_STATIC) }
                ?: return

            // checks for violation
            if (MEMBER_POSITIONS.getOrDefault(lastType, -1) > MEMBER_POSITIONS[child.type]!!) {
                log(
                    child,
                    Messages.get(
                        MSG,
                        MEMBER_ARGUMENTS[child.type]!!,
                        MEMBER_ARGUMENTS[lastType]!!,
                    ),
                )
            }

            lastType = child.type
        }
    }

    internal companion object {
        const val MSG = "class.member.ordering"
        private const val MSG_PROPERTY = "property"
        private const val MSG_CONSTRUCTOR = "constructor"
        private const val MSG_FUNCTION = "function"

        private val MEMBER_POSITIONS =
            mapOf(
                VARIABLE_DEF to 0,
                CTOR_DEF to 1,
                COMPACT_CTOR_DEF to 1,
                METHOD_DEF to 2,
            )

        private val MEMBER_ARGUMENTS =
            mapOf(
                VARIABLE_DEF to Messages[MSG_PROPERTY],
                CTOR_DEF to Messages[MSG_CONSTRUCTOR],
                COMPACT_CTOR_DEF to Messages[MSG_CONSTRUCTOR],
                METHOD_DEF to Messages[MSG_FUNCTION],
            )
    }
}
