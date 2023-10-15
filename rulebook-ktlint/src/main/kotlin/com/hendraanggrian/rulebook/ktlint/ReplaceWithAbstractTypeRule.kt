package com.hendraanggrian.rulebook.ktlint

import com.pinterest.ktlint.rule.engine.core.api.ElementType.DESTRUCTURING_DECLARATION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.REFERENCE_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.TYPE_REFERENCE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.USER_TYPE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.VALUE_PARAMETER
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/ReplaceWithAbstractType).
 */
class ReplaceWithAbstractTypeRule : RulebookRule("replace-with-abstract-type") {
    internal companion object {
        const val MSG_COLLECTION = "replace.with.abstract.type.collection"
        const val MSG_IO = "replace.with.abstract.type.io"
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
        val identifier = node.findChildByType(TYPE_REFERENCE)
            ?.findChildByType(USER_TYPE)
            ?.findChildByType(REFERENCE_EXPRESSION)
            ?.findChildByType(IDENTIFIER) ?: return

        // report if explicit collection is found
        when (identifier.text) {
            "ArrayList" -> emit(
                identifier.startOffset,
                Messages.get(MSG_COLLECTION, "List", "MutableList"),
                false
            )
            "HashSet", "TreeSet" -> emit(
                identifier.startOffset,
                Messages.get(MSG_COLLECTION, "Set", "MutableSet"),
                false
            )
            "HashMap", "TreeMap" -> emit(
                identifier.startOffset,
                Messages.get(MSG_COLLECTION, "Map", "MutableMap"),
                false
            )
            "FileInputStream", "ByteArrayInputStream" -> emit(
                identifier.startOffset,
                Messages.get(MSG_IO, "InputStream"),
                false
            )
            "FileOutputStream", "ByteArrayOutputStream" -> emit(
                identifier.startOffset,
                Messages.get(MSG_IO, "OutputStream"),
                false
            )
        }
    }
}
