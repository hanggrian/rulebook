package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CATCH
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.REFERENCE_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.TYPE_REFERENCE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.USER_TYPE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.VALUE_PARAMETER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.VALUE_PARAMETER_LIST
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#exception-subclass-catching)
 */
public class ExceptionSubclassCatchingRule : Rule("exception-subclass-catching") {
    override val tokens: TokenSet = TokenSet.create(CATCH)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // checks for violation
        val identifier =
            node
                .findChildByType(VALUE_PARAMETER_LIST)
                ?.findChildByType(VALUE_PARAMETER)
                ?.findChildByType(TYPE_REFERENCE)
                ?.findChildByType(USER_TYPE)
                ?.findChildByType(REFERENCE_EXPRESSION)
                ?.findChildByType(IDENTIFIER)
                ?.takeIf { it.text in BROAD_EXCEPTIONS }
                ?: return
        emit(identifier.startOffset, Messages[MSG], false)
    }

    internal companion object {
        const val MSG = "exception.subclass.catching"

        private val BROAD_EXCEPTIONS =
            setOf(
                "Exception",
                "Error",
                "Throwable",
                "java.lang.Exception",
                "java.lang.Error",
                "java.lang.Throwable",
                "kotlin.Exception",
                "kotlin.Error",
                "kotlin.Throwable",
            )
    }
}
