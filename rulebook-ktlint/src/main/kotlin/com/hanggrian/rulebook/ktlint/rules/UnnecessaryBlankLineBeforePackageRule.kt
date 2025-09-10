package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.Messages
import com.hanggrian.rulebook.ktlint.RulebookRuleSet
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FILE
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.isWhiteSpaceWithNewline
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/** [See detail](https://hanggrian.github.io/rulebook/rules/#unnecessary-blank-line-before-package) */
public class UnnecessaryBlankLineBeforePackageRule : RulebookRule(ID) {
    override val tokens: TokenSet = TokenSet.create(FILE)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // checks for violation
        val whitespace =
            node
                .firstChildNode
                ?.takeIf { it.isWhiteSpaceWithNewline() }
                ?: return
        emit(whitespace.startOffset, Messages[MSG], false)
    }

    public companion object {
        public val ID: RuleId =
            RuleId("${RulebookRuleSet.ID.value}:unnecessary-blank-line-before-package")

        private const val MSG = "unnecessary.blank.line.before.package"
    }
}
