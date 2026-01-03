package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.Messages
import com.hanggrian.rulebook.ktlint.RulebookRuleSet
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS_BODY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FILE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FUN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.children20
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet
import org.jetbrains.kotlin.psi.psiUtil.children

/** [See detail](https://hanggrian.github.io/rulebook/rules/#overload-function-position) */
public class OverloadFunctionPositionRule : RulebookRule(ID) {
    override val tokens: TokenSet = TokenSet.create(FILE, CLASS_BODY)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // collect functions
        val funs =
            node
                .children20
                .filter { it.elementType == FUN }
                .toList()

        // checks for violation
        val declaredIdentifiers = mutableSetOf<String>()
        for ((i, `fun`) in funs.withIndex()) {
            val identifier = `fun`.findChildByType(IDENTIFIER) ?: continue
            if (funs.getOrNull(i - 1)?.findChildByType(IDENTIFIER)?.text != identifier.text &&
                !declaredIdentifiers.add(identifier.text)
            ) {
                emit(`fun`.startOffset, Messages[MSG, identifier.text], false)
            }
        }
    }

    public companion object {
        public val ID: RuleId = RuleId("${RulebookRuleSet.ID.value}:overload-function-position")

        private const val MSG = "overload.function.position"
    }
}
