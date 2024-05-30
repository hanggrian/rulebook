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
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#constructor-position)
 */
public class ConstructorPositionCheck : Check() {
    override fun getRequiredTokens(): IntArray =
        intArrayOf(
            CLASS_DEF,
            INTERFACE_DEF,
            ENUM_DEF,
            ANNOTATION_DEF,
        )

    override fun visitToken(node: DetailAST) {
        // there may be multiple constructors in JVM, target class instead for efficiency
        val constructor = node.findFirstToken(OBJBLOCK).findFirstToken(CTOR_DEF) ?: return

        // checks for violation
        constructor.siblings(true).filter { it.type == VARIABLE_DEF }
            .forEach { log(it, Messages[MSG_PROPERTIES]) }
        constructor.siblings(false).filter { it.type == METHOD_DEF }
            .forEach { log(it, Messages[MSG_METHODS]) }
    }

    internal companion object {
        const val MSG_PROPERTIES = "constructor.position.properties"
        const val MSG_METHODS = "constructor.position.methods"
    }
}
