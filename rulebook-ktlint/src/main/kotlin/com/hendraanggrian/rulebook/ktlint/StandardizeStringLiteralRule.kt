package com.hendraanggrian.rulebook.ktlint

import com.pinterest.ktlint.rule.engine.core.api.ElementType.STRING_TEMPLATE
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/UncommonStringLiteral).
 */
class StandardizeStringLiteralRule : RulebookRule("uncommon-string-literal") {
    internal companion object {
        const val MSG_ENCODING = "standardize.string.literal.encoding"
        const val MSG_COLOR = "standardize.string.literal.color"

        val PROHIBITED_ENCODINGS = setOf("\"ascii\"", "\"utf-8\"", "\"utf-16\"", "\"utf-32\"")
        val COLOR_REGEX = "^\"#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})\"$"
    }

    private var colorRegex: Regex? = null

    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit
    ) {
        // first line of filter
        if (node.elementType != STRING_TEMPLATE) {
            return
        }

        // initialize regex
        if (colorRegex == null) {
            colorRegex = Regex(COLOR_REGEX)
        }

        val literal = node.text
        when {
            // encoding must be all uppercase
            literal in PROHIBITED_ENCODINGS -> emit(
                node.startOffset,
                Messages.get(MSG_ENCODING, literal.trimQuotes().uppercase()),
                false
            )
            // color must be all lowercase
            colorRegex!!.matches(literal) -> {
                // skip '"#'
                if (literal.drop(2).any { it.isUpperCase() }) {
                    emit(
                        node.startOffset,
                        Messages.get(MSG_COLOR, literal.trimQuotes().lowercase()),
                        false
                    )
                }
            }
        }
    }

    private fun String.trimQuotes(): String = substring(1, lastIndex)
}
