package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.hendraanggrian.rulebook.ktlint.internals.contains
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS_BODY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS_INITIALIZER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FUN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.PROPERTY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.PROPERTY_ACCESSOR
import com.pinterest.ktlint.rule.engine.core.api.ElementType.SECONDARY_CONSTRUCTOR
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.psi.psiUtil.siblings

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#constructor-position)
 */
public class ConstructorPositionRule : Rule("constructor-position") {
    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit,
    ) {
        // first line of filter
        if (node.elementType != CLASS_BODY) {
            return
        }

        // there may be multiple constructors in JVM, target class instead for efficiency
        val constructor = node.findChildByType(SECONDARY_CONSTRUCTOR) ?: return

        // checks for violation
        constructor.siblings(true).filter { it.isPropertyOrInitializer() }
            .forEach { emit(it.startOffset, Messages[MSG_PROPERTIES], false) }
        constructor.siblings(false).filter { it.isMethod() }
            .forEach { emit(it.startOffset, Messages[MSG_METHODS], false) }
    }

    internal companion object {
        const val MSG_PROPERTIES = "constructor.position.properties"
        const val MSG_METHODS = "constructor.position.methods"

        private fun ASTNode.isPropertyOrInitializer(): Boolean {
            if (elementType == PROPERTY) {
                return PROPERTY_ACCESSOR !in this
            }
            return elementType == CLASS_INITIALIZER
        }

        private fun ASTNode.isMethod(): Boolean {
            if (elementType == PROPERTY) {
                return PROPERTY_ACCESSOR in this
            }
            return elementType == FUN
        }
    }
}
