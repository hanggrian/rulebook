package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.hendraanggrian.rulebook.ktlint.internals.contains
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FUN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.TYPE_PARAMETER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.TYPE_PARAMETER_LIST
import com.pinterest.ktlint.rule.engine.core.api.children
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#rename-uncommon-generics).
 */
class RenameUncommonGenericsRule : RulebookRule("rename-uncommon-generics") {
    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit,
    ) {
        // first line of filter
        if (node.elementType != CLASS && node.elementType != FUN) {
            return
        }

        // filter out multiple generics
        val typeParameterList = node.findChildByType(TYPE_PARAMETER_LIST) ?: return
        val typeParameter =
            typeParameterList.children().singleOrNull { it.elementType == TYPE_PARAMETER } ?: return

        // check for a match
        val identifier = typeParameter.findChildByType(IDENTIFIER) ?: return
        if (!node.hasParentWithGenerics() && identifier.text !in COMMON_GENERICS) {
            emit(identifier.startOffset, Messages[MSG], false)
        }
    }

    internal companion object {
        const val MSG = "rename.uncommon.generics"

        private val COMMON_GENERICS = setOf("E", "K", "N", "T", "V")

        private fun ASTNode.hasParentWithGenerics(): Boolean {
            var next: ASTNode? = treeParent
            while (next != null) {
                if (TYPE_PARAMETER_LIST in next) {
                    return true
                }
                next = next.treeParent
            }
            return false
        }
    }
}
