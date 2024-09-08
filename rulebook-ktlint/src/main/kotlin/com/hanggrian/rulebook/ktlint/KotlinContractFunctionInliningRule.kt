package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Messages
import com.hanggrian.rulebook.ktlint.internals.hasModifier
import com.pinterest.ktlint.rule.engine.core.api.ElementType.BLOCK
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CALL_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FUN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.INLINE_KEYWORD
import com.pinterest.ktlint.rule.engine.core.api.ElementType.REFERENCE_EXPRESSION
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#kotlin-contract-function-inlining)
 */
public class KotlinContractFunctionInliningRule : Rule("kotlin-contract-function-inlining") {
    override val tokens: TokenSet = TokenSet.create(FUN)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // find initial contract call
        node
            .findChildByType(BLOCK)
            ?.findChildByType(CALL_EXPRESSION)
            ?.takeIf { n ->
                n
                    .findChildByType(REFERENCE_EXPRESSION)
                    ?.findChildByType(IDENTIFIER)
                    ?.text
                    .let { it == "contract" || it == "kotlin.contract" }
            } ?: return

        // checks for violation
        node
            .takeUnless { it.hasModifier(INLINE_KEYWORD) }
            ?: return
        emit(node.startOffset, Messages[MSG], false)
    }

    internal companion object {
        const val MSG = "kotlin.contract.function.inlining"
    }
}
