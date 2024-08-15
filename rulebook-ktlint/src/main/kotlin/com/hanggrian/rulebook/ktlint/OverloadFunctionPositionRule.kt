package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS_BODY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FILE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FUN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet
import org.jetbrains.kotlin.psi.psiUtil.children

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#overload-function-position)
 */
public class OverloadFunctionPositionRule : Rule("overload-function-position") {
    override val tokens: TokenSet = TokenSet.create(FILE, CLASS_BODY)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // collect functions
        val functions = node.children().filter { it.elementType == FUN }

        val declaredIdentifiers = mutableSetOf<String>()
        var lastIdentifier: String? = null
        for (function in functions) {
            // checks for violation
            val name = function.findChildByType(IDENTIFIER)!!.text
            if (lastIdentifier != name && !declaredIdentifiers.add(name)) {
                emit(function.startOffset, Messages.get(MSG, name), false)
            }

            // keep variable instead iterating set until last
            lastIdentifier = name
        }
    }

    internal companion object {
        const val MSG = "overload.function.position"
    }
}
