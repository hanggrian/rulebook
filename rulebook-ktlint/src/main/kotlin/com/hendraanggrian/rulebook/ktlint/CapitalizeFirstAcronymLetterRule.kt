package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.hendraanggrian.rulebook.ktlint.internals.getFileName
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FILE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FUN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.OBJECT_DECLARATION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.PROPERTY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.VALUE_PARAMETER
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#capitalize-first-acronym-letter).
 */
public class CapitalizeFirstAcronymLetterRule : RulebookRule("capitalize-first-acronym-letter") {
    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit,
    ) {
        // first line of filter
        when (node.elementType) {
            CLASS, OBJECT_DECLARATION, PROPERTY, FUN, VALUE_PARAMETER -> {
                // retrieve name
                val identifier = node.findChildByType(IDENTIFIER) ?: return

                // allow all uppercase, which usually is static property
                if (node.elementType == PROPERTY && identifier.text.isStaticPropertyName()) {
                    return
                }

                // checks for violation
                if (ABBREVIATION_REGEX.containsMatchIn(identifier.text)) {
                    emit(
                        identifier.startOffset,
                        Messages.get(MSG, identifier.text.transform()),
                        false,
                    )
                }
            }
            FILE -> {
                // retrieve name
                val fileName = getFileName(node) ?: return

                // checks for violation
                if (ABBREVIATION_REGEX.containsMatchIn(fileName)) {
                    emit(node.startOffset, Messages.get(MSG, fileName.transform()), false)
                }
            }
        }
    }

    internal companion object {
        const val MSG = "capitalize.first.acronym.letter"

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
