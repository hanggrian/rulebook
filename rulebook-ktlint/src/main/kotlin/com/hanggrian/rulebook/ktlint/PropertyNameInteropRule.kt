package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Messages
import com.hanggrian.rulebook.ktlint.internals.contains
import com.pinterest.ktlint.rule.engine.core.api.ElementType.ANNOTATION_ENTRY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS_BODY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CONSTRUCTOR_CALLEE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.INTERNAL_KEYWORD
import com.pinterest.ktlint.rule.engine.core.api.ElementType.MODIFIER_LIST
import com.pinterest.ktlint.rule.engine.core.api.ElementType.OBJECT_DECLARATION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.PRIMARY_CONSTRUCTOR
import com.pinterest.ktlint.rule.engine.core.api.ElementType.PRIVATE_KEYWORD
import com.pinterest.ktlint.rule.engine.core.api.ElementType.PROPERTY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.PROPERTY_ACCESSOR
import com.pinterest.ktlint.rule.engine.core.api.ElementType.REFERENCE_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.TYPE_REFERENCE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.USER_TYPE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.VALUE_PARAMETER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.VALUE_PARAMETER_LIST
import com.pinterest.ktlint.rule.engine.core.api.ElementType.VAL_KEYWORD
import com.pinterest.ktlint.rule.engine.core.api.ElementType.VAR_KEYWORD
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.children
import com.pinterest.ktlint.rule.engine.core.api.hasModifier
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/** [See detail](https://hanggrian.github.io/rulebook/rules/#property-name-interop) */
public class PropertyNameInteropRule : RulebookRule(ID) {
    override val tokens: TokenSet = TokenSet.create(CLASS, OBJECT_DECLARATION)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // collect fields declared in constructor
        val properties = mutableListOf<ASTNode>()
        if (node.elementType == CLASS) {
            properties +=
                node
                    .findChildByType(PRIMARY_CONSTRUCTOR)
                    ?.findChildByType(VALUE_PARAMETER_LIST)
                    ?.children()
                    ?.filter {
                        it.elementType == VALUE_PARAMETER &&
                            (VAL_KEYWORD in it || VAR_KEYWORD in it)
                    } ?: return
        }

        // collect fields declared in block
        val classBody = node.findChildByType(CLASS_BODY)
        if (classBody != null) {
            properties +=
                classBody
                    .children()
                    .filter { it.elementType == PROPERTY }
        }

        // checks for violation
        for (property in properties.filterNot {
            it.hasModifier(PRIVATE_KEYWORD) ||
                it.hasModifier(INTERNAL_KEYWORD) ||
                it.hasAnnotation("JvmField") ||
                it.findChildByType(PROPERTY_ACCESSOR)?.hasAnnotation("JvmName") == true
        }) {
            val identifier =
                property
                    .findChildByType(IDENTIFIER)
                    ?.takeUnless { it.text.startsWith("is") }
                    ?.takeIf {
                        property
                            .findChildByType(TYPE_REFERENCE)
                            ?.findChildByType(USER_TYPE)
                            ?.findChildByType(REFERENCE_EXPRESSION)
                            ?.findChildByType(IDENTIFIER)
                            ?.text == "Boolean"
                    } ?: continue
            emit(
                identifier.startOffset,
                Messages.get(MSG, "is" + identifier.text.replaceFirstChar { it.uppercaseChar() }),
                false,
            )
        }
    }

    internal companion object {
        val ID = RuleId("${RulebookRuleSet.ID.value}:property-name-interop")

        const val MSG = "property.name.interop"

        private fun ASTNode.hasAnnotation(name: String) =
            findChildByType(MODIFIER_LIST)
                ?.children()
                .orEmpty()
                .filter { it.elementType == ANNOTATION_ENTRY }
                .any {
                    it
                        .findChildByType(CONSTRUCTOR_CALLEE)
                        ?.findChildByType(TYPE_REFERENCE)
                        ?.findChildByType(USER_TYPE)
                        ?.findChildByType(REFERENCE_EXPRESSION)
                        ?.findChildByType(IDENTIFIER)
                        ?.text == name
                }
    }
}
