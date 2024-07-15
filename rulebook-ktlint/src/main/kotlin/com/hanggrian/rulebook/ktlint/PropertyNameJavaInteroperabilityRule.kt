package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Emit
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.rule.engine.core.api.ElementType.ANNOTATION_ENTRY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS_BODY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CONSTRUCTOR_CALLEE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.MODIFIER_LIST
import com.pinterest.ktlint.rule.engine.core.api.ElementType.OBJECT_DECLARATION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.PRIMARY_CONSTRUCTOR
import com.pinterest.ktlint.rule.engine.core.api.ElementType.PROPERTY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.PROPERTY_ACCESSOR
import com.pinterest.ktlint.rule.engine.core.api.ElementType.REFERENCE_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.TYPE_REFERENCE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.USER_TYPE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.VALUE_PARAMETER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.VALUE_PARAMETER_LIST
import com.pinterest.ktlint.rule.engine.core.api.RuleAutocorrectApproveHandler
import com.pinterest.ktlint.rule.engine.core.api.children
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#property-name-java-interoperability)
 */
public class PropertyNameJavaInteroperabilityRule :
    Rule("property-name-java-interoperability"),
    RuleAutocorrectApproveHandler {
    override fun beforeVisitChildNodes(node: ASTNode, emit: Emit) {
        // first line of filter
        if (node.elementType != CLASS &&
            node.elementType != OBJECT_DECLARATION
        ) {
            return
        }

        // collect fields declared in constructor
        val properties = mutableListOf<ASTNode>()
        if (node.elementType == CLASS) {
            val valueParameterList =
                node
                    .findChildByType(PRIMARY_CONSTRUCTOR)
                    ?.findChildByType(VALUE_PARAMETER_LIST)
                    ?: return
            properties += valueParameterList.children().filter { it.elementType == VALUE_PARAMETER }
        }

        // collect fields declared in block
        val classBody = node.findChildByType(CLASS_BODY)
        if (classBody != null) {
            properties += classBody.children().filter { it.elementType == PROPERTY }
        }

        // checks for violation
        for (property in properties.filterNot {
            it.hasAnnotation("JvmField") ||
                it.findChildByType(PROPERTY_ACCESSOR)?.hasAnnotation("JvmName") == true
        }) {
            val identifier =
                property
                    .findChildByType(IDENTIFIER)
                    ?.takeIf { !it.text.startsWith("is") }
                    ?: continue
            property
                .findChildByType(TYPE_REFERENCE)
                ?.findChildByType(USER_TYPE)
                ?.findChildByType(REFERENCE_EXPRESSION)
                ?.findChildByType(IDENTIFIER)
                ?.takeIf { it.text == "Boolean" }
                ?: continue
            emit(
                identifier.startOffset,
                Messages.get(
                    MSG,
                    "is" + identifier.text.replaceFirstChar { it.uppercaseChar() },
                ),
                false,
            )
        }
    }

    internal companion object {
        const val MSG = "property.name.java.interoperability"

        private fun ASTNode.hasAnnotation(name: String): Boolean =
            findChildByType(MODIFIER_LIST)
                ?.children()
                ?.filter { it.elementType == ANNOTATION_ENTRY }
                ?.any {
                    it
                        .findChildByType(CONSTRUCTOR_CALLEE)
                        ?.findChildByType(TYPE_REFERENCE)
                        ?.findChildByType(USER_TYPE)
                        ?.findChildByType(REFERENCE_EXPRESSION)
                        ?.findChildByType(IDENTIFIER)
                        ?.text == name
                } ?: false
    }
}
