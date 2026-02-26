package com.hanggrian.rulebook.internal

import com.hanggrian.rulebook.ktlint.rules.Emit
import com.hanggrian.rulebook.ktlint.rules.RulebookRule
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FUN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.VALUE_PARAMETER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.VALUE_PARAMETER_LIST
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.children20
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

class MethodParameterNameRule : RulebookRule(ID) {
    override val tokens: TokenSet = TokenSet.create(FUN)

    override fun visitToken(node: ASTNode, emit: Emit) {
        val funName =
            node
                .findChildByType(IDENTIFIER)
                ?.text
                ?: return
        val paramIdentifier =
            node
                .findChildByType(VALUE_PARAMETER_LIST)
                ?.children20
                ?.firstOrNull { it.elementType == VALUE_PARAMETER }
                ?.findChildByType(IDENTIFIER)
                ?: return

        val name =
            when {
                funName == "beforeFirstNode" -> "editorConfig"
                funName == "applyTo" -> "code"
                funName.startsWith("visit") -> "node"
                else -> return
            }
        paramIdentifier
            .takeUnless { it.text == name }
            ?: return
        emit(paramIdentifier.startOffset, "Rename parameter to '$name'.", false)
    }

    companion object {
        val ID: RuleId = RuleId("${CodecheckRuleSet.ID.value}:method-parameter-name")
    }
}
