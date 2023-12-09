package com.hendraanggrian.rulebook.checkstyle

import com.hendraanggrian.rulebook.checkstyle.internals.Messages
import com.hendraanggrian.rulebook.checkstyle.internals.siblings
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.ANNOTATION_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.CLASS_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.CTOR_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.ENUM_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.INTERFACE_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.METHOD_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.OBJBLOCK
import com.puppycrawl.tools.checkstyle.api.TokenTypes.VARIABLE_DEF

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#constructor-position).
 */
public class ConstructorPositionCheck : RulebookCheck() {
    public override fun getRequiredTokens(): IntArray =
        intArrayOf(
            CLASS_DEF,
            INTERFACE_DEF,
            ENUM_DEF,
            ANNOTATION_DEF,
        )

    public override fun visitToken(node: DetailAST) {
        // avoid directly targeting constructor for efficiency
        val constructor = node.findFirstToken(OBJBLOCK).findFirstToken(CTOR_DEF) ?: return

        // checks for violation
        if (constructor.siblings(true).any { it.type == VARIABLE_DEF }) {
            log(constructor, Messages[MSG_PROPERTIES])
        }
        if (constructor.siblings(false).any { it.type == METHOD_DEF }) {
            log(constructor, Messages[MSG_METHODS])
        }
    }

    internal companion object {
        const val MSG_PROPERTIES = "constructor.position.properties"
        const val MSG_METHODS = "constructor.position.methods"
    }
}