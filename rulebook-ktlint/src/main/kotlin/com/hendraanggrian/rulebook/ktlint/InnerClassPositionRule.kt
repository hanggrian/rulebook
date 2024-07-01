package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.internals.Emit
import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.hendraanggrian.rulebook.ktlint.internals.hasModifier
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS_BODY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS_INITIALIZER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.COMPANION_KEYWORD
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FUN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.OBJECT_DECLARATION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.PROPERTY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.SECONDARY_CONSTRUCTOR
import com.pinterest.ktlint.rule.engine.core.api.RuleAutocorrectApproveHandler
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.psi.psiUtil.siblings

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#inner-class-position)
 */
public class InnerClassPositionRule :
    Rule("inner-class-position"),
    RuleAutocorrectApproveHandler {
    override fun beforeVisitChildNodes(node: ASTNode, emit: Emit) {
        // first line of filter
        if (node.elementType != CLASS) {
            return
        }

        // consider only inner class
        node.treeParent
            .takeIf { it.elementType == CLASS_BODY }
            ?.treeParent
            ?.takeIf { it.elementType == CLASS }
            ?: return

        // in Kotlin, static members belong in companion object
        for (child in node.siblings()) {
            // capture child types
            if (child.elementType != PROPERTY &&
                child.elementType != CLASS_INITIALIZER &&
                child.elementType != SECONDARY_CONSTRUCTOR &&
                child.elementType != FUN &&
                child.elementType != OBJECT_DECLARATION
            ) {
                continue
            }

            // skip non-companion object
            if (child.elementType == OBJECT_DECLARATION && !child.hasModifier(COMPANION_KEYWORD)) {
                continue
            }

            // report once
            emit(node.startOffset, Messages[MSG], false)
            return
        }
    }

    internal companion object {
        const val MSG = "inner.class.position"
    }
}
