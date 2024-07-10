package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Emit
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.hanggrian.rulebook.ktlint.internals.contains
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CONSTRUCTOR_CALLEE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.REFERENCE_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.SUPER_TYPE_CALL_ENTRY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.SUPER_TYPE_ENTRY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.SUPER_TYPE_LIST
import com.pinterest.ktlint.rule.engine.core.api.ElementType.TYPE_REFERENCE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.USER_TYPE
import com.pinterest.ktlint.rule.engine.core.api.RuleAutocorrectApproveHandler
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#exception-extending)
 */
public class ExceptionExtendingRule :
    Rule("exception-extending"),
    RuleAutocorrectApproveHandler {
    override fun beforeVisitChildNodes(node: ASTNode, emit: Emit) {
        // first line of filter
        if (node.elementType != CLASS) {
            return
        }

        // get identifier from supertype declared with or without constructor callee
        val superTypeList = node.findChildByType(SUPER_TYPE_LIST) ?: return
        val identifier =
            when {
                SUPER_TYPE_CALL_ENTRY in superTypeList ->
                    superTypeList
                        .findChildByType(SUPER_TYPE_CALL_ENTRY)
                        ?.findChildByType(CONSTRUCTOR_CALLEE)
                        ?.findChildByType(TYPE_REFERENCE)
                SUPER_TYPE_ENTRY in superTypeList ->
                    superTypeList
                        .findChildByType(SUPER_TYPE_ENTRY)
                        ?.findChildByType(TYPE_REFERENCE)
                else -> null
            }?.findChildByType(USER_TYPE)
                ?.findChildByType(REFERENCE_EXPRESSION)
                ?.findChildByType(IDENTIFIER)
                ?: return

        // checks for violation
        identifier.takeIf { it.text in NON_APPLICATION_EXCEPTIONS } ?: return
        emit(identifier.startOffset, Messages[MSG], false)
    }

    internal companion object {
        const val MSG = "exception.extending"

        private val NON_APPLICATION_EXCEPTIONS =
            setOf(
                "Error",
                "Throwable",
                "java.lang.Error",
                "java.lang.Throwable",
                "kotlin.Error",
                "kotlin.Throwable",
            )
    }
}
