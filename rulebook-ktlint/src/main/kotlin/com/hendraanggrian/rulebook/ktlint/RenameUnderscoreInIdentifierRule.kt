package com.hendraanggrian.rulebook.ktlint

import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FUN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.OBJECT_DECLARATION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.PROPERTY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.VALUE_PARAMETER
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/RenameUnderscoreInIdentifier).
 */
class RenameUnderscoreInIdentifierRule : RulebookRule("rename-underscore-in-identifier") {
    internal companion object {
        const val MSG = "rename.underscore.in.identifier"

        val REGEX = Regex("_+")
    }

    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit
    ) {
        // first line of filter
        if (node.elementType != CLASS &&
            node.elementType != OBJECT_DECLARATION &&
            node.elementType != PROPERTY &&
            node.elementType != FUN &&
            node.elementType != VALUE_PARAMETER
        ) {
            return
        }

        // retrieve name
        val identifier = node.findChildByType(IDENTIFIER) ?: return

        // allow all uppercase, which usually is static property
        if (node.elementType == PROPERTY && identifier.text.isStaticPropertyName()) {
            return
        }

        // find underscore
        if ('_' in identifier.text) {
            emit(identifier.startOffset, Messages.get(MSG, identifier.text.transform()), false)
        }
    }

    private fun String.transform(): String {
        val sb = StringBuilder(this)
        REGEX.findAll(this).asIterable().reversed().forEach {
            when {
                it.range.last == lastIndex -> sb.removeRange(it.range)
                else -> sb.replace(
                    it.range.first,
                    it.range.last + 2,
                    get(it.range.last + 1).titlecase()
                )
            }
        }
        return sb.toString()
    }
}
