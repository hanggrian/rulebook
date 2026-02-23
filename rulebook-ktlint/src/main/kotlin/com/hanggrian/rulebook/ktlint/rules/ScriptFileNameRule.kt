package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.Messages
import com.hanggrian.rulebook.ktlint.RulebookRuleSet
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FILE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.SCRIPT
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.parent
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet
import java.io.File

/** [See detail](https://hanggrian.github.io/rulebook/rules/#script-file-name) */
public class ScriptFileNameRule : RulebookRule(ID) {
    override val tokens: TokenSet = TokenSet.create(SCRIPT)

    override fun isScript(): Boolean = true

    override fun visitToken(node: ASTNode, emit: Emit) {
        // get file token
        val file =
            node
                .parent { it.elementType == FILE }
                ?: return

        // checks for violation
        val name =
            node
                .psi
                .containingFile
                .name
                .substringAfterLast(File.separator)
                .substringBefore(".kts")
                .substringBefore(".gradle")
        val nameReplacement =
            name
                .replace(REGEX, "$1-$2")
                .replace('_', '-')
                .lowercase()
                .takeUnless { it == name }
                ?: return
        emit(file.startOffset, Messages[MSG, nameReplacement], false)
    }

    public companion object {
        public val ID: RuleId = RuleId("${RulebookRuleSet.ID.value}:script-file-name")
        private const val MSG = "script.file.name"

        private val REGEX = Regex("([a-z0-9])([A-Z])")
    }
}
