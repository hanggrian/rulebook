package com.hanggrian.rulebook.checkstyle.checks

import com.hanggrian.rulebook.checkstyle.Messages
import com.hanggrian.rulebook.checkstyle.properties.ConfigurableWords
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.ANNOTATION_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.CLASS_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.ENUM_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.IDENT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.INTERFACE_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.RECORD_DEF

/** [See detail](https://hanggrian.github.io/rulebook/rules/#meaningless-word) */
public class MeaninglessWordCheck :
    RulebookAstCheck(),
    ConfigurableWords {
    override val wordSet: HashSet<String> =
        hashSetOf("Util", "Utility", "Helper", "Manager", "Wrapper")

    override fun getRequiredTokens(): IntArray =
        intArrayOf(
            CLASS_DEF,
            INTERFACE_DEF,
            ANNOTATION_DEF,
            ENUM_DEF,
            RECORD_DEF,
        )

    override fun visitToken(node: DetailAST) {
        // checks for violation
        val ident = node.findFirstToken(IDENT) ?: return
        val finalName =
            wordSet
                .singleOrNull { ident.text.endsWith(it) }
                ?: return
        ident
            .takeIf { finalName in UTILITY_FINAL_NAMES }
            ?.let {
                log(
                    it,
                    Messages[MSG_UTIL, it.text.substringBefore(finalName) + 's'],
                )
                return
            }
        log(ident, Messages[MSG_ALL, finalName])
    }

    private companion object {
        const val MSG_ALL = "meaningless.word.all"
        const val MSG_UTIL = "meaningless.word.util"

        val UTILITY_FINAL_NAMES = hashSetOf("Util", "Utility")
    }
}
