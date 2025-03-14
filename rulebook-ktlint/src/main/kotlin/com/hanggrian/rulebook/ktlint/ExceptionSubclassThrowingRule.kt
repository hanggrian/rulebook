package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CALL_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.REFERENCE_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.THROW
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/** [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#exception-subclass-throwing) */
public class ExceptionSubclassThrowingRule : RulebookRule(ID) {
    override val tokens: TokenSet = TokenSet.create(THROW)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // checks for violation
        val identifier =
            node
                .findChildByType(CALL_EXPRESSION)
                ?.findChildByType(REFERENCE_EXPRESSION)
                ?.findChildByType(IDENTIFIER)
                ?.takeIf { it.text in BROAD_EXCEPTIONS }
                ?: return
        emit(identifier.startOffset, Messages[MSG], false)
    }

    internal companion object {
        val ID = RuleId("${RulebookRuleSet.ID.value}:exception-subclass-throwing")

        const val MSG = "exception.subclass.throwing"

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
