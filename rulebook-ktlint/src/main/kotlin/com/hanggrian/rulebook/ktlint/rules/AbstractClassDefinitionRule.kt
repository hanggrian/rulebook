package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.Messages
import com.hanggrian.rulebook.ktlint.RulebookRuleSet
import com.hanggrian.rulebook.ktlint.contains
import com.pinterest.ktlint.rule.engine.core.api.ElementType.ABSTRACT_KEYWORD
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS_BODY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FUN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.MODIFIER_LIST
import com.pinterest.ktlint.rule.engine.core.api.ElementType.PROPERTY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.SUPER_TYPE_LIST
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.children20
import com.pinterest.ktlint.rule.engine.core.api.hasModifier
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/** [See detail](https://hanggrian.github.io/rulebook/rules/#abstract-class-definition) */
public class AbstractClassDefinitionRule : RulebookRule(ID) {
    override val tokens: TokenSet = TokenSet.create(CLASS)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // skip non-abstract class
        val abstractKeyword =
            node
                .findChildByType(MODIFIER_LIST)
                ?.findChildByType(ABSTRACT_KEYWORD)
                ?: return

        // checks for violation
        node
            .takeIf { n ->
                SUPER_TYPE_LIST !in n &&
                    n
                        .findChildByType(CLASS_BODY)
                        ?.children20
                        .orEmpty()
                        .none {
                            (it.elementType === FUN || it.elementType === PROPERTY) &&
                                it.hasModifier(ABSTRACT_KEYWORD)
                        }
            } ?: return
        emit(abstractKeyword.startOffset, Messages[MSG], false)
    }

    public companion object {
        public val ID: RuleId = RuleId("${RulebookRuleSet.ID.value}:abstract-class-definition")
        private const val MSG = "abstract.class.definition"
    }
}
