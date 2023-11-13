package com.hendraanggrian.rulebook.checkstyle

import com.hendraanggrian.rulebook.checkstyle.internals.Messages
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.ANNOTATION_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.CLASS_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.ENUM_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.IDENT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.INTERFACE_DEF

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#avoid-meaningless-word).
 */
public class AvoidMeaninglessWordCheck : RulebookCheck() {
    public override fun getRequiredTokens(): IntArray =
        intArrayOf(
            CLASS_DEF,
            INTERFACE_DEF,
            ENUM_DEF,
            ANNOTATION_DEF,
        )

    public override fun visitToken(node: DetailAST) {
        // Retrieve name.
        val ident = node.findFirstToken(IDENT) ?: return
        val matches = TITLE_CASE_REGEX.findAll(ident.text)
        val prefix = matches.first().value
        val suffix = matches.last().value

        // Find meaningless words.
        if (prefix in RESTRICTED_PREFIXES) {
            log(ident, Messages.get(MSG_PREFIX, prefix))
        }
        if (suffix in RESTRICTED_SUFFIXES) {
            log(ident, Messages.get(MSG_SUFFIX, suffix))
        }
    }

    internal companion object {
        const val MSG_PREFIX = "avoid.meaningless.word.prefix"
        const val MSG_SUFFIX = "avoid.meaningless.word.suffix"

        private val RESTRICTED_PREFIXES = setOf("Abstract", "Base", "Generic")
        private val RESTRICTED_SUFFIXES =
            setOf("Util", "Utility", "Manager", "Helper", "Wrapper", "Data", "Info")
        private val TITLE_CASE_REGEX =
            Regex("((^[a-z]+)|([0-9]+)|([A-Z]{1}[a-z]+)|([A-Z]+(?=([A-Z][a-z])|(\$)|([0-9]))))")
    }
}
