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
            val primaryConstructor =
                node
                    .takeIf { it.isPublic() }
                    ?.findChildByType(PRIMARY_CONSTRUCTOR)
                    ?: return
            properties +=
                primaryConstructor
                    .takeIf { it.isPublic() }
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
        for (property in properties.filter {
            it.isPublic() &&
                !it.hasAnnotation("JvmField") &&
                it.findChildByType(PROPERTY_ACCESSOR)?.hasAnnotation("JvmName") != true
        }) {
            val identifier =
                property
                    .findChildByType(IDENTIFIER)
                    ?.takeIf {
                        !it.text.startsWith("is") &&
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

    public companion object {
        public val ID: RuleId = RuleId("${RulebookRuleSet.ID.value}:property-name-interop")

        private const val MSG = "property.name.interop"

        private fun ASTNode.hasAnnotation(name: String) =
            findChildByType(MODIFIER_LIST)
                ?.children()
                .orEmpty()
                .any {
                    it.elementType == ANNOTATION_ENTRY &&
                        it
                            .findChildByType(CONSTRUCTOR_CALLEE)
                            ?.findChildByType(TYPE_REFERENCE)
                            ?.findChildByType(USER_TYPE)
                            ?.findChildByType(REFERENCE_EXPRESSION)
                            ?.findChildByType(IDENTIFIER)
                            ?.text == name
                }

        private fun ASTNode.isPublic() =
            !hasModifier(PRIVATE_KEYWORD) &&
                !hasModifier(INTERNAL_KEYWORD)
    }
}
