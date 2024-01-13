package com.hendraanggrian.rulebook.checkstyle

import com.hendraanggrian.rulebook.checkstyle.internals.Messages
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.ANNOTATION_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.CLASS_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.ENUM_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.IDENT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.INTERFACE_DEF

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#source-word-meaning).
 */
public class SourceWordMeaningCheck : RulebookCheck() {
    private var meaninglessWords =
        setOf(
            "Abstract",
            "Base",
            "Util",
            "Utility",
            "Helper",
            "Manager",
            "Wrapper",
            "Data",
            "Info",
        )
    private var ignoredWords = emptySet<String>()

    public fun setMeaninglessWords(vararg words: String) {
        meaninglessWords = words.toSet()
    }

    public fun setIgnoredWords(vararg words: String) {
        ignoredWords = words.toSet()
    }

    override fun getRequiredTokens(): IntArray =
        intArrayOf(
            CLASS_DEF,
            INTERFACE_DEF,
            ENUM_DEF,
            ANNOTATION_DEF,
        )

    override fun visitToken(node: DetailAST) {
        // get identifier name
        val (name, ast) = node.findFirstToken(IDENT)?.let { it.text to it } ?: return

        // checks for violation
        TITLE_CASE_REGEX.findAll(name)
            .filter { it.value in meaninglessWords && it.value !in ignoredWords }
            .forEach { log(ast, Messages.get(MSG, it.value)) }
    }

    internal companion object {
        const val MSG = "source.word.meaning"

        private val TITLE_CASE_REGEX =
            Regex("((^[a-z]+)|([0-9]+)|([A-Z]{1}[a-z]+)|([A-Z]+(?=([A-Z][a-z])|(\$)|([0-9]))))")
    }
}
