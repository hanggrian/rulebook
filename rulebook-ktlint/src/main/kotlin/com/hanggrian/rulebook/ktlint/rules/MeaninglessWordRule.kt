package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.Messages
import com.hanggrian.rulebook.ktlint.RulebookRuleSet
import com.hanggrian.rulebook.ktlint.properties.MEANINGLESS_WORDS_PROPERTY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.OBJECT_DECLARATION
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.EditorConfig
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/** [See detail](https://hanggrian.github.io/rulebook/rules/#meaningless-word) */
public class MeaninglessWordRule : RulebookRule(ID, MEANINGLESS_WORDS_PROPERTY) {
    private var words = MEANINGLESS_WORDS_PROPERTY.defaultValue

    override val tokens: TokenSet = TokenSet.create(CLASS, OBJECT_DECLARATION)

    override fun beforeFirstNode(editorConfig: EditorConfig) {
        words = editorConfig[MEANINGLESS_WORDS_PROPERTY]
    }

    override fun visitToken(node: ASTNode, emit: Emit) {
        // obtain corresponding full and final name
        val identifier =
            node
                .findChildByType(IDENTIFIER)
                ?: return

        // checks for violation
        val fullName = identifier.text
        val finalName =
            words
                .singleOrNull { fullName.endsWith(it) }
                ?: return
        if (finalName in UTILITY_FINAL_NAMES) {
            emit(
                identifier.startOffset,
                Messages[MSG_UTIL, fullName.substringBefore(finalName) + 's'],
                false,
            )
            return
        }
        emit(identifier.startOffset, Messages[MSG_ALL, finalName], false)
    }

    public companion object {
        public val ID: RuleId = RuleId("${RulebookRuleSet.ID.value}:meaningless-word")
        private const val MSG_ALL = "meaningless.word.all"
        private const val MSG_UTIL = "meaningless.word.util"

        private val UTILITY_FINAL_NAMES = hashSetOf("Util", "Utility")
    }
}
