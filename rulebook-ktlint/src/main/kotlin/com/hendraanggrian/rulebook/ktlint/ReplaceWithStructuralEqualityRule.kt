package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.rule.engine.core.api.ElementType.EQEQEQ
import com.pinterest.ktlint.rule.engine.core.api.ElementType.EXCLEQEQEQ
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#replace-with-structural-equality).
 */
class ReplaceWithStructuralEqualityRule : RulebookRule("replace-with-structural-equality") {
    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit,
    ) {
        // checks for violation
        when (node.elementType) {
            EQEQEQ -> emit(node.startOffset, Messages.get(MSG, "=="), false)
            EXCLEQEQEQ -> emit(node.startOffset, Messages.get(MSG, "!="), false)
        }
    }

    internal companion object {
        const val MSG = "replace.with.structural.equality"
    }
}
