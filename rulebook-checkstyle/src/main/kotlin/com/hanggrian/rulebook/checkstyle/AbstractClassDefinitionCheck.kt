package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.hanggrian.rulebook.checkstyle.internals.children
import com.hanggrian.rulebook.checkstyle.internals.contains
import com.hanggrian.rulebook.checkstyle.internals.hasModifier
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.ABSTRACT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.CLASS_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.EXTENDS_CLAUSE
import com.puppycrawl.tools.checkstyle.api.TokenTypes.IMPLEMENTS_CLAUSE
import com.puppycrawl.tools.checkstyle.api.TokenTypes.METHOD_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.MODIFIERS
import com.puppycrawl.tools.checkstyle.api.TokenTypes.OBJBLOCK

/** [See detail](https://hanggrian.github.io/rulebook/rules/#abstract-class-definition) */
public class AbstractClassDefinitionCheck : RulebookAstCheck() {
    override fun getRequiredTokens(): IntArray = intArrayOf(CLASS_DEF)

    override fun visitToken(node: DetailAST) {
        // skip non-abstract class
        val abstract =
            node
                .findFirstToken(MODIFIERS)
                ?.findFirstToken(ABSTRACT)
                ?: return

        // checks for violation
        node
            .takeIf { n ->
                EXTENDS_CLAUSE !in n &&
                    IMPLEMENTS_CLAUSE !in n &&
                    n
                        .findFirstToken(OBJBLOCK)
                        ?.children()
                        .orEmpty()
                        .none { it.type == METHOD_DEF && it.hasModifier(ABSTRACT) }
            } ?: return
        log(abstract, Messages[MSG])
    }

    private companion object {
        const val MSG = "abstract.class.definition"
    }
}
