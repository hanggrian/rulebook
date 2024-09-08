package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.hanggrian.rulebook.checkstyle.internals.children
import com.hanggrian.rulebook.checkstyle.internals.maxLineNo
import com.hanggrian.rulebook.checkstyle.internals.minLineNo
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.ANNOTATION_FIELD_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.CLASS_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.COMPACT_CTOR_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.CTOR_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.ENUM_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.INTERFACE_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.METHOD_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.OBJBLOCK
import com.puppycrawl.tools.checkstyle.api.TokenTypes.RECORD_COMPONENT_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.RECORD_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.STATIC_INIT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.VARIABLE_DEF

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#member-line-spacing)
 */
public class MemberLineSpacingCheck : Check() {
    override fun getRequiredTokens(): IntArray = intArrayOf(OBJBLOCK)

    override fun isCommentNodesRequired(): Boolean = true

    override fun visitToken(node: DetailAST) {
        // need at least 1 child
        val children =
            node.children
                .filter { it.type in DECLARATION_ARGUMENTS.keys }
                .toList()
                .takeUnless { it.isEmpty() }
                ?: return

        for ((i, child) in children.withIndex()) {
            // skip first
            i
                .takeUnless { it == 0 }
                ?: continue

            // checks for violation
            val msgKey = DECLARATION_ARGUMENTS[child.type]!!
            children[i - 1]
                .takeUnless { msgKey == MSG_PROPERTY && it.type == child.type }
                ?.takeUnless { it.maxLineNo == child.minLineNo - 2 }
                ?: continue
            log(child, Messages.get(MSG, msgKey))
        }
    }

    internal companion object {
        const val MSG = "member.line.spacing"
        private const val MSG_CLASS = "class"
        private const val MSG_CONSTRUCTOR = "constructor"
        private const val MSG_FUNCTION = "function"
        private const val MSG_PROPERTY = "property"
        private const val MSG_STATIC = "static"

        private val DECLARATION_ARGUMENTS =
            mapOf(
                CLASS_DEF to MSG_CLASS,
                INTERFACE_DEF to MSG_CLASS,
                ENUM_DEF to MSG_CLASS,
                RECORD_DEF to MSG_CLASS,
                CTOR_DEF to MSG_CONSTRUCTOR,
                COMPACT_CTOR_DEF to MSG_CONSTRUCTOR,
                COMPACT_CTOR_DEF to MSG_CONSTRUCTOR,
                METHOD_DEF to MSG_FUNCTION,
                VARIABLE_DEF to MSG_PROPERTY,
                ANNOTATION_FIELD_DEF to MSG_PROPERTY,
                RECORD_COMPONENT_DEF to MSG_PROPERTY,
                STATIC_INIT to MSG_STATIC,
            )
    }
}
