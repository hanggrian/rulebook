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
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#source-acronym-capitalization).
 */
public class SourceAcronymCapitalizationRule : RulebookRule("source-acronym-capitalization") {
    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit,
    ) {
        // first line of filter
        if (node.elementType != FILE &&
            node.elementType != CLASS &&
            node.elementType != OBJECT_DECLARATION &&
            node.elementType != PROPERTY &&
            node.elementType != FUN &&
            node.elementType != VALUE_PARAMETER
        ) {
            return
        }

        // get file or identifier name
        val (name, ast) =
            when (node.elementType) {
                FILE -> (getFileName(node) ?: return) to node
                else ->
                    node.findChildByType(IDENTIFIER)
                        ?.takeUnless {
                            // allow all uppercase, which usually is static property
                            node.elementType == PROPERTY && STATIC_PROPERTY_REGEX.matches(it.text)
                        }
                        ?.let { it.text to it }
                        ?: return
            }

        // checks for violation
        val replacement =
            name.takeIf { ACRONYM_REGEX.containsMatchIn(it) }
                ?.run {
                    ACRONYM_REGEX.replace(this) {
                        it.value.first() +
                            when {
                                it.range.last == lastIndex -> it.value.drop(1).lowercase()
                                else -> it.value.drop(1).dropLast(1).lowercase() + it.value.last()
                            }
                    }
                }
                ?: return
        emit(ast.startOffset, Messages.get(MSG, replacement), false)
    }

    internal companion object {
        const val MSG = "source.acronym.capitalization"

        private val ACRONYM_REGEX = Regex("[A-Z]{3,}")
        private val STATIC_PROPERTY_REGEX = Regex("^[A-Z][A-Z0-9_]*\$")
    }
}
