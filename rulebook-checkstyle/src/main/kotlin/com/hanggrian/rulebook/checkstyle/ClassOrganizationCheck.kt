package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.hanggrian.rulebook.checkstyle.internals.children
import com.hanggrian.rulebook.checkstyle.internals.hasModifier
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.ANNOTATION_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.CLASS_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.COMPACT_CTOR_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.CTOR_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.ENUM_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.INTERFACE_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_STATIC
import com.puppycrawl.tools.checkstyle.api.TokenTypes.METHOD_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.OBJBLOCK
import com.puppycrawl.tools.checkstyle.api.TokenTypes.VARIABLE_DEF

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#class-organization)
 */
public class ClassOrganizationCheck : Check() {
    override fun getRequiredTokens(): IntArray =
        intArrayOf(
            CLASS_DEF,
            INTERFACE_DEF,
            ENUM_DEF,
            ANNOTATION_DEF,
        )

    override fun visitToken(node: DetailAST) {
        var lastType = -1
        for (child in node.findFirstToken(OBJBLOCK)!!.children.filter {
            it.type == VARIABLE_DEF ||
                it.type == CTOR_DEF ||
                it.type == COMPACT_CTOR_DEF ||
                it.type == METHOD_DEF
        }) {
            // in Java, static members have specific keyword
            if (child.hasModifier(LITERAL_STATIC)) {
                continue
            }

            // checks for violation
            if (ELEMENT_POSITIONS.getOrDefault(lastType, -1) > ELEMENT_POSITIONS[child.type]!!) {
                log(
                    child,
                    Messages.get(
                        MSG,
                        ELEMENT_ARGUMENTS[child.type]!!,
                        ELEMENT_ARGUMENTS[lastType]!!,
                    ),
                )
            }

            lastType = child.type
        }
    }

    internal companion object {
        const val MSG = "class.organization"
        private const val MSG_PROPERTY = "property"
        private const val MSG_CONSTRUCTOR = "constructor"
        private const val MSG_FUNCTION = "function"

        private val ELEMENT_POSITIONS =
            mapOf(
                VARIABLE_DEF to 0,
                CTOR_DEF to 1,
                COMPACT_CTOR_DEF to 1,
                METHOD_DEF to 2,
            )

        private val ELEMENT_ARGUMENTS =
            mapOf(
                VARIABLE_DEF to Messages[MSG_PROPERTY],
                CTOR_DEF to Messages[MSG_CONSTRUCTOR],
                COMPACT_CTOR_DEF to Messages[MSG_CONSTRUCTOR],
                METHOD_DEF to Messages[MSG_FUNCTION],
            )
    }
}
