package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS_BODY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FILE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FUN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet
import org.jetbrains.kotlin.psi.psiUtil.children

/** [See detail](https://hanggrian.github.io/rulebook/rules/all/#overload-function-position) */
public class OverloadFunctionPositionRule : RulebookRule(ID) {
    override val tokens: TokenSet = TokenSet.create(FILE, CLASS_BODY)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // collect functions
        val funs =
            node
                .children()
                .filter { it.elementType == FUN }

        val declaredIdentifiers = mutableSetOf<String>()
        var lastIdentifier: ASTNode? = null
        for (`fun` in funs) {
            // checks for violation
            val identifier = `fun`.findChildByType(IDENTIFIER)!!
            if (lastIdentifier?.text != identifier.text &&
                !declaredIdentifiers.add(identifier.text)
            ) {
                emit(`fun`.startOffset, Messages.get(MSG, identifier.text), false)
            }

            // keep variable instead iterating set until last
            lastIdentifier = identifier
        }
    }

    internal companion object {
        val ID = RuleId("${RulebookRuleSet.ID.value}:overload-function-position")

        const val MSG = "overload.function.position"
    }
}
