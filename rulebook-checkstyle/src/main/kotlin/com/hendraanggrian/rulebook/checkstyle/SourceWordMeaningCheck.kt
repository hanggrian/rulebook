package com.hendraanggrian.rulebook.checkstyle

import com.hendraanggrian.rulebook.checkstyle.internals.Messages
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.ANNOTATION_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.CLASS_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.ENUM_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.IDENT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.INTERFACE_DEF

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#source-word-meaning)
 */
public class SourceWordMeaningCheck : RulebookCheck() {
    private var meaninglessWords = setOf("Util", "Utility", "Helper", "Manager", "Wrapper")

    public fun setMeaninglessWords(vararg words: String) {
        meaninglessWords = words.toSet()
    }

    override fun getRequiredTokens(): IntArray =
        intArrayOf(
            CLASS_DEF,
            INTERFACE_DEF,
            ENUM_DEF,
            ANNOTATION_DEF,
        )

    override fun visitToken(node: DetailAST) {
        // checks for violation
        val ident = node.findFirstToken(IDENT) ?: return
        TITLE_CASE_REGEX.findAll(ident.text)
            .filter { it.value in meaninglessWords }
            .forEach {
                when (val word = it.value) {
                    "Util", "Utility" ->
                        log(ident, Messages.get(MSG_UTIL, ident.text.substringBefore(word) + 's'))
                    else -> log(ident, Messages.get(MSG_ALL, word))
                }
            }
    }

    internal companion object {
        const val MSG_ALL = "source.word.meaning.all"
        const val MSG_UTIL = "source.word.meaning.util"

        private val TITLE_CASE_REGEX =
            Regex("((^[a-z]+)|([0-9]+)|([A-Z]{1}[a-z]+)|([A-Z]+(?=([A-Z][a-z])|(\$)|([0-9]))))")
    }
}
