package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Messages
import com.hanggrian.rulebook.ktlint.internals.hasModifier
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS_BODY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS_INITIALIZER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.COMPANION_KEYWORD
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FUN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.OBJECT_DECLARATION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.PROPERTY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.SECONDARY_CONSTRUCTOR
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet
import org.jetbrains.kotlin.psi.psiUtil.siblings

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#inner-class-position)
 */
public class InnerClassPositionRule : RulebookRule(ID) {
    override val tokens: TokenSet = TokenSet.create(CLASS)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // consider only inner class
        node
            .treeParent
            .takeIf { it.elementType == CLASS_BODY }
            ?.treeParent
            ?.takeIf { it.elementType == CLASS }
            ?: return

        // in Kotlin, static members belong in companion object
        for (child in node.siblings()) {
            // capture child types
            child
                .takeUnless {
                    it.elementType != PROPERTY &&
                        it.elementType != CLASS_INITIALIZER &&
                        it.elementType != SECONDARY_CONSTRUCTOR &&
                        it.elementType != FUN &&
                        it.elementType != OBJECT_DECLARATION
                } ?: continue

            // checks for violation
            child
                .takeUnless {
                    it.elementType == OBJECT_DECLARATION &&
                        !it.hasModifier(COMPANION_KEYWORD)
                } ?: continue
            emit(node.startOffset, Messages[MSG], false)
            return
        }
    }

    internal companion object {
        val ID = RuleId("${RulebookRuleSet.ID.value}:inner-class-position")

        const val MSG = "inner.class.position"
    }
}
