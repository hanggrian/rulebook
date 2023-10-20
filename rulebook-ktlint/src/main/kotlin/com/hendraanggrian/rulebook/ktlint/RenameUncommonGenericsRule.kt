package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.hendraanggrian.rulebook.ktlint.internals.RulebookRule
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.TYPE_PARAMETER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.TYPE_PARAMETER_LIST
import com.pinterest.ktlint.rule.engine.core.api.children
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/RenameUncommonGenerics).
 */
class RenameUncommonGenericsRule : RulebookRule("rename-uncommon-generics") {
    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit,
    ) {
        // first line of filter
        if (node.elementType != TYPE_PARAMETER_LIST) {
            return
        }

        // filter out multiple generics
        val typeParameter =
            node.children().singleOrNull { it.elementType == TYPE_PARAMETER } ?: return

        // check for a match
        val identifier = typeParameter.findChildByType(IDENTIFIER) ?: return
        if (identifier.text !in COMMON_GENERICS) {
            emit(identifier.startOffset, Messages[MSG], false)
        }
    }

    internal companion object {
        const val MSG = "rename.uncommon.generics"

        private val COMMON_GENERICS = setOf("E", "K", "N", "T", "V")
    }
}
