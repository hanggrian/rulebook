package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.BROAD_EXCEPTIONS
import com.hanggrian.rulebook.ktlint.internals.Emit
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CATCH
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.REFERENCE_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.TYPE_REFERENCE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.USER_TYPE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.VALUE_PARAMETER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.VALUE_PARAMETER_LIST
import com.pinterest.ktlint.rule.engine.core.api.RuleAutocorrectApproveHandler
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#exception-subclass-catching)
 */
public class ExceptionSubclassCatchingRule :
    Rule("exception-subclass-catching"),
    RuleAutocorrectApproveHandler {
    override fun beforeVisitChildNodes(node: ASTNode, emit: Emit) {
        // first line of filter
        if (node.elementType != CATCH) {
            return
        }

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
    }
}
