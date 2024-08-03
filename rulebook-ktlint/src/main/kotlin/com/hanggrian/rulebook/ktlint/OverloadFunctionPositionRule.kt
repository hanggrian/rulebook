package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FUN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#overload-function-position)
 */
public class OverloadFunctionPositionRule : Rule("overload-function-position") {
    private val declaredIdentifiers = mutableSetOf<String>()
    private var lastIdentifier: String? = null

    override val tokens: TokenSet = TokenSet.create(FUN)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // checks for violation
        val name = node.findChildByType(IDENTIFIER)!!.text
        if (lastIdentifier != name && !declaredIdentifiers.add(name)) {
            emit(node.startOffset, Messages.get(MSG, name), false)
        }

        // keep variable instead iterating set until last
        lastIdentifier = name
    }

    internal companion object {
        const val MSG = "overload.function.position"
    }
}
