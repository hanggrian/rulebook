package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.BROAD_EXCEPTIONS
import com.hanggrian.rulebook.ktlint.internals.Emit
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CALL_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.REFERENCE_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.THROW
import com.pinterest.ktlint.rule.engine.core.api.RuleAutocorrectApproveHandler
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#exception-subclass-throwing)
 */
public class ExceptionSubclassThrowingRule :
    Rule("exception-subclass-throwing"),
    RuleAutocorrectApproveHandler {
    override fun beforeVisitChildNodes(node: ASTNode, emit: Emit) {
        // first line of filter
        if (node.elementType != THROW) {
            return
        }

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
        const val MSG = "exception.subclass.throwing"
    }
}
