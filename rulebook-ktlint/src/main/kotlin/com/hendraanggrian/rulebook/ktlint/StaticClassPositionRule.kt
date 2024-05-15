package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS_INITIALIZER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.COMPANION_KEYWORD
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FUN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.PROPERTY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.SECONDARY_CONSTRUCTOR
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.psi.psiUtil.siblings

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#static-class-position)
 */
public class StaticClassPositionRule : RulebookRule("static-class-position") {
    public override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit,
    ) {
        // first line of filter
        if (node.elementType != COMPANION_KEYWORD) {
            return
        }

        // checks for violation
        val class2 = node.treeParent.treeParent
        class2.takeIf { n ->
            n.siblings().any {
                it.elementType == PROPERTY ||
                    it.elementType == CLASS_INITIALIZER ||
                    it.elementType == SECONDARY_CONSTRUCTOR ||
                    it.elementType == FUN
            }
        } ?: return
        emit(class2.startOffset, Messages[MSG], false)
    }

    internal companion object {
        const val MSG = "static.class.position"
    }
}
