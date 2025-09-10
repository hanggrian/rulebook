package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.Messages
import com.hanggrian.rulebook.ktlint.RulebookRuleSet
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CONSTRUCTOR_CALLEE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.REFERENCE_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.SUPER_TYPE_CALL_ENTRY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.SUPER_TYPE_ENTRY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.SUPER_TYPE_LIST
import com.pinterest.ktlint.rule.engine.core.api.ElementType.TYPE_REFERENCE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.USER_TYPE
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/** [See detail](https://hanggrian.github.io/rulebook/rules/#exception-inheritance) */
public class ExceptionInheritanceRule : RulebookRule(ID) {
    override val tokens: TokenSet = TokenSet.create(CLASS)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // get identifier from supertype declared with or without constructor callee
        val superTypeList =
            node
                .findChildByType(SUPER_TYPE_LIST)
                ?: return
        val typeReference =
            superTypeList
                .findChildByType(SUPER_TYPE_CALL_ENTRY)
                ?.findChildByType(CONSTRUCTOR_CALLEE)
                ?.findChildByType(TYPE_REFERENCE)
                ?: superTypeList
                    .findChildByType(SUPER_TYPE_ENTRY)
                    ?.findChildByType(TYPE_REFERENCE)
                ?: return
        val identifier =
            typeReference
                .findChildByType(USER_TYPE)
                ?.findChildByType(REFERENCE_EXPRESSION)
                ?.findChildByType(IDENTIFIER)
                ?: return

        // checks for violation
        identifier
            .text
            .takeIf { it in NON_APPLICATION_EXCEPTIONS }
            ?: return
        emit(identifier.startOffset, Messages[MSG], false)
    }

    public companion object {
        public val ID: RuleId = RuleId("${RulebookRuleSet.ID.value}:exception-inheritance")

        private const val MSG = "exception.inheritance"

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
