package com.hendraanggrian.rulebook.ktlint

import com.pinterest.ktlint.rule.engine.core.api.ElementType.DESTRUCTURING_DECLARATION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.REFERENCE_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.TYPE_REFERENCE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.USER_TYPE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.VALUE_PARAMETER
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/UseAbstractCollection).
 */
class UseAbstractCollectionRule : RulebookRule("use-abstract-collection") {
    internal companion object {
        const val MSG_LIST = "use.abstract.collection.list"
        const val MSG_SET = "use.abstract.collection.set"
        const val MSG_MAP = "use.abstract.collection.map"
    }

    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit
    ) {
        // first line of filter
        if (node.elementType != VALUE_PARAMETER) {
            return
        }

        // destructuring is not considered function or constructor declaration
        if (DESTRUCTURING_DECLARATION in node) {
            return
        }

        // only take type reference
        val identifier = node.getOrNull(TYPE_REFERENCE)
            ?.getOrNull(USER_TYPE)
            ?.getOrNull(REFERENCE_EXPRESSION)
            ?.getOrNull(IDENTIFIER) ?: return

        // report if explicit collection is found
        when (identifier.text) {
            "ArrayList" -> emit(identifier.startOffset, Messages[MSG_LIST], false)
            "HashSet", "TreeSet" -> emit(identifier.startOffset, Messages[MSG_SET], false)
            "HashMap", "TreeMap" -> emit(identifier.startOffset, Messages[MSG_MAP], false)
        }
    }
}
