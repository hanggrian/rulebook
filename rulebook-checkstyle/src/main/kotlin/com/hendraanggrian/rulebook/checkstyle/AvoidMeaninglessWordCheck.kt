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
    private var words =
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
    private var excludeWords = emptySet<String>()

    public fun setWords(vararg words: String) {
        this.words = words.toSet()
    }

    public fun setExcludeWords(vararg excludeWords: String) {
        this.excludeWords = excludeWords.toSet()
    }

    public override fun getRequiredTokens(): IntArray =
        intArrayOf(
            CLASS_DEF,
            INTERFACE_DEF,
            ENUM_DEF,
            ANNOTATION_DEF,
        )

    public override fun visitToken(node: DetailAST) {
        // checks for violation
        val ident = node.findFirstToken(IDENT) ?: return
        TITLE_CASE_REGEX.findAll(ident.text)
            .filter { it.value in words && it.value !in excludeWords }
            .forEach { log(ident, Messages.get(MSG, it.value)) }
    }

    internal companion object {
        const val MSG = "avoid.meaningless.word"

        private val TITLE_CASE_REGEX =
            Regex("((^[a-z]+)|([0-9]+)|([A-Z]{1}[a-z]+)|([A-Z]+(?=([A-Z][a-z])|(\$)|([0-9]))))")
    }
}
