package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.Messages
import com.hanggrian.rulebook.ktlint.RulebookRuleSet
import com.hanggrian.rulebook.ktlint.contains
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FUN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.TYPE_PARAMETER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.TYPE_PARAMETER_LIST
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.children20
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/** [See detail](https://hanggrian.github.io/rulebook/rules/#generic-name) */
public class GenericNameRule : RulebookRule(ID) {
    override val tokens: TokenSet = TokenSet.create(CLASS, FUN)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // filter out multiple generics
        val typeParameter =
            node
                .findChildByType(TYPE_PARAMETER_LIST)
                ?.children20
                ?.singleOrNull { it.elementType === TYPE_PARAMETER }
                ?: return

        // checks for violation
        val identifier =
            typeParameter
                .findChildByType(IDENTIFIER)
                ?.takeUnless {
                    node.hasParentWithGenerics() ||
                        it.text.singleOrNull()?.isUpperCase() == true
                } ?: return
        emit(identifier.startOffset, Messages[MSG], false)
    }

    public companion object {
        public val ID: RuleId = RuleId("${RulebookRuleSet.ID.value}:generic-name")
        private const val MSG = "generic.name"

        private fun ASTNode.hasParentWithGenerics(): Boolean {
            var next = treeParent
            while (next != null) {
                next
                    .takeUnless { TYPE_PARAMETER_LIST in it }
                    ?: return true
                next = next.treeParent
            }
            return false
        }
    }
}
