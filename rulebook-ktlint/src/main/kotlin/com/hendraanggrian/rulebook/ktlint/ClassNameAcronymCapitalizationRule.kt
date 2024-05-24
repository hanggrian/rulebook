package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.hendraanggrian.rulebook.ktlint.internals.getFileName
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FILE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.OBJECT_DECLARATION
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#class-name-acronym-capitalization)
 */
public class ClassNameAcronymCapitalizationRule :
    RulebookRule("class-name-acronym-capitalization") {
    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit,
    ) {
        // first line of filter
        when (node.elementType) {
            CLASS, OBJECT_DECLARATION -> {
                // checks for violation
                val identifier =
                    node.findChildByType(IDENTIFIER)
                        ?.takeIf { ABBREVIATION_REGEX.containsMatchIn(it.text) }
                        ?: return
                emit(
                    identifier.startOffset,
                    Messages.get(MSG, identifier.text.transform()),
                    false,
                )
            }
            FILE -> {
                // checks for violation
                val fileName =
                    getFileName(node)
                        ?.takeIf { ABBREVIATION_REGEX.containsMatchIn(it) }
                        ?: return
                emit(node.startOffset, Messages.get(MSG, fileName.transform()), false)
            }
        }
    }

    internal companion object {
        const val MSG = "class.name.acronym.capitalization"

        private val ABBREVIATION_REGEX = Regex("[A-Z]{3,}")

        private fun String.isStaticPropertyName(): Boolean =
            all { it.isUpperCase() || it.isDigit() || it == '_' }

        private fun String.transform(): String =
            ABBREVIATION_REGEX.replace(this) {
                it.value.first() +
                    when {
                        it.range.last == lastIndex -> it.value.drop(1).lowercase()
                        else -> it.value.drop(1).dropLast(1).lowercase() + it.value.last()
                    }
            }
    }
}
