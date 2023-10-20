package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.hendraanggrian.rulebook.ktlint.internals.RulebookRule
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.OBJECT_DECLARATION
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/RenameMeaninglessWord).
 */
class RenameMeaninglessWordRule : RulebookRule("rename-meaningless-word") {
    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit,
    ) {
        // first line of filter
        if (node.elementType != CLASS && node.elementType != OBJECT_DECLARATION) {
            return
        }

        // retrieve name
        val identifier = node.findChildByType(IDENTIFIER) ?: return
        val matches = TITLE_CASE_REGEX.findAll(identifier.text)
        val prefix = matches.first().value
        val suffix = matches.last().value

        // find meaningless words
        if (prefix in RESTRICTED_PREFIXES) {
            emit(identifier.startOffset, Messages.get(MSG_PREFIX, prefix), false)
        }
        if (suffix in RESTRICTED_SUFFIXES) {
            emit(identifier.startOffset, Messages.get(MSG_SUFFIX, suffix), false)
        }
    }

    internal companion object {
        const val MSG_PREFIX = "rename.meaningless.word.prefix"
        const val MSG_SUFFIX = "rename.meaningless.word.suffix"

        private val RESTRICTED_PREFIXES = setOf("Abstract", "Base", "Generic")
        private val RESTRICTED_SUFFIXES =
            setOf("Util", "Utility", "Manager", "Helper", "Wrapper", "Data", "Info")
        private val TITLE_CASE_REGEX =
            Regex("((^[a-z]+)|([0-9]+)|([A-Z]{1}[a-z]+)|([A-Z]+(?=([A-Z][a-z])|(\$)|([0-9]))))")
    }
}
