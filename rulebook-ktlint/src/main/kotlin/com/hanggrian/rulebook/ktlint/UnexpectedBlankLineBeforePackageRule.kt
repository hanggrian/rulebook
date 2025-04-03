package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FILE
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.isWhiteSpaceWithNewline
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/** [See detail](https://hanggrian.github.io/rulebook/rules/#unexpected-blank-line-before-package) */
public class UnexpectedBlankLineBeforePackageRule : RulebookRule(ID) {
    override val tokens: TokenSet = TokenSet.create(FILE)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // checks for violation
        node
            .firstChildNode
            ?.takeIf { it.isWhiteSpaceWithNewline() }
            ?: return
        emit(node.startOffset, Messages[MSG], false)
    }

    public companion object {
        public val ID: RuleId =
            RuleId("${RulebookRuleSet.ID.value}:unexpected-blank-line-before-package")

        private const val MSG = "unexpected.blank.line.before.package"
    }
}
