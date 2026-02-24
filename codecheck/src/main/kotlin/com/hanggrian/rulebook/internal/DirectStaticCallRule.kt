package com.hanggrian.rulebook.internal

import com.hanggrian.rulebook.ktlint.rules.Emit
import com.hanggrian.rulebook.ktlint.rules.RulebookRule
import com.pinterest.ktlint.rule.engine.core.api.ElementType.DOT_QUALIFIED_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IMPORT_DIRECTIVE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.PROPERTY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.REFERENCE_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.children20
import com.pinterest.ktlint.rule.engine.core.api.isPartOf
import com.pinterest.ktlint.rule.engine.core.api.parent
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

class DirectStaticCallRule : RulebookRule(ID) {
    override val tokens: TokenSet = TokenSet.create(DOT_QUALIFIED_EXPRESSION)

    override fun visitToken(node: ASTNode, emit: Emit) {
        node
            .takeUnless { it.isPartOf(IMPORT_DIRECTIVE) }
            ?: return
        node
            .takeUnless { n ->
                n
                    .parent { it.elementType === PROPERTY }
                    ?.findChildByType(IDENTIFIER)
                    ?.text == "ID"
            } ?: return
        node
            .findChildByType(DOT_QUALIFIED_EXPRESSION)
            ?.children20
            ?.filter { it.elementType === REFERENCE_EXPRESSION }
            ?.mapNotNull { it.findChildByType(IDENTIFIER) }
            ?.takeUnless { n -> n.any { it.text == "ID" } }
            ?: return

        val identifier =
            node
                .findChildByType(REFERENCE_EXPRESSION)
                ?.findChildByType(IDENTIFIER)
                ?.takeIf {
                    it.text.let { s ->
                        s != "AllRules" &&
                            s.first().isUpperCase() &&
                            s.any { c -> c.isLowerCase() }
                    }
                } ?: return
        emit(identifier.startOffset, "Call static member directly.", false)
    }

    companion object {
        val ID: RuleId = RuleId("${CodecheckRuleSet.ID.value}:direct-static-call")
    }
}
