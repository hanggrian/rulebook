package com.hanggrian.rulebook.internal

import com.hanggrian.rulebook.ktlint.rules.Emit
import com.hanggrian.rulebook.ktlint.rules.RulebookRule
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_SECTION
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

class UrlInDocumentationRule : RulebookRule(ID) {
    override val tokens: TokenSet = TokenSet.create(CLASS)

    override fun visitToken(node: ASTNode, emit: Emit) {
        var className =
            node
                .findChildByType(IDENTIFIER)
                ?.text
                ?.takeUnless { it.startsWith("Rulebook") }
                ?: return
        className =
            when {
                className.endsWith("Rule") -> className.substringBeforeLast("Rule")
                className.endsWith("Check") -> className.substringBeforeLast("Check")
                else -> return
            }
        val link =
            node
                .findChildByType(KDOC)
                ?.findChildByType(KDOC_SECTION)
        if (link == null) {
            emit(node.startOffset, "Missing URL.", false)
            return
        }
        className
            .replace(REGEX, "$1-$2")
            .lowercase()
            .takeUnless {
                link.text == " [See detail](https://hanggrian.github.io/rulebook/rules/#$it) "
            } ?: return
        emit(node.startOffset, "Invalid URL.", false)
    }

    companion object {
        val ID: RuleId = RuleId("${CodecheckRuleSet.ID.value}:url-in-kdoc")

        val REGEX = Regex("([a-z])([A-Z])")
    }
}
