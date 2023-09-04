package com.hendraanggrian.rulebook.ktlint

import com.pinterest.ktlint.rule.engine.core.api.ElementType.FUN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.PROPERTY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.VALUE_PARAMETER
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/NoUnderscoreName).
 */
class NoUnderscoreNameRule : RulebookRule("no-underscore-name") {
    internal companion object {
        const val MSG = "no.underscore.name"
    }

    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit
    ) {
        // first line of filter
        if (node.elementType != PROPERTY &&
            node.elementType != FUN &&
            node.elementType != VALUE_PARAMETER
        ) {
            return
        }

        // allow all uppercase, which usually is static property
        val identifier = node.getOrNull(IDENTIFIER) ?: return
        if (node.elementType == PROPERTY && identifier.text.isStaticPropertyName()) {
            return
        }

        // find underscore
        if ('_' in identifier.text) {
            emit(identifier.startOffset, Messages.get(MSG, identifier.text.transform()), false)
        }
    }

    private fun String.transform(): String {
        val sb = StringBuilder()
        var isUppercase = false
        forEach { c ->
            isUppercase = when {
                c == '_' -> true
                else -> {
                    sb.append(
                        when {
                            isUppercase -> c.uppercase()
                            else -> c
                        }
                    )
                    false
                }
            }
        }
        return sb.toString()
    }
}
