package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.Messages
import com.hanggrian.rulebook.ktlint.RulebookRuleSet
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CALL_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.DOT_QUALIFIED_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.REFERENCE_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.nextSibling
import com.pinterest.ktlint.rule.engine.core.api.parent
import com.pinterest.ktlint.rule.engine.core.api.prevSibling
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/** [See detail](https://hanggrian.github.io/rulebook/rules/#eager-api) */
public class EagerApiRule : RulebookRule(ID) {
    override val tokens: TokenSet = TokenSet.create(CALL_EXPRESSION)

    override fun isScript(): Boolean = true

    override fun visitToken(node: ASTNode, emit: Emit) {
        // collect callee
        val calleeIdentifier =
            node
                .prevSibling { it.elementType === REFERENCE_EXPRESSION }
                ?.findChildByType(IDENTIFIER)
                ?: node
                    .parent {
                        it.elementType === CALL_EXPRESSION ||
                            it.elementType === DOT_QUALIFIED_EXPRESSION
                    }?.findChildByType(REFERENCE_EXPRESSION)
                    ?.findChildByType(IDENTIFIER)
                ?: return
        val callee = calleeIdentifier.text

        // checks for violation
        val identifier =
            node
                .findChildByType(REFERENCE_EXPRESSION)
                ?.findChildByType(IDENTIFIER)
                ?: return
        val call = identifier.text
        val callReplacement =
            when (callee) {
                BUILDSCRIPT_CALLEE -> BUILDSCRIPT_CALL_REPLACEMENT
                TASK_CALLEE -> TASK_CALL_REPLACEMENT[call]
                in DOMAIN_OBJECT_CALLEES -> DOMAIN_OBJECTS_CALL_REPLACEMENT[call]
                else -> null
            } ?: return
        callReplacement
            .takeUnless { s ->
                s == "configureEach" &&
                    node
                        .parent
                        ?.nextSibling { it.elementType === CALL_EXPRESSION }
                        ?.findChildByType(REFERENCE_EXPRESSION)
                        ?.findChildByType(IDENTIFIER)
                        ?.text == s
            } ?: return
        emit(node.startOffset, Messages[MSG, callReplacement], false)
    }

    public companion object {
        public val ID: RuleId = RuleId("${RulebookRuleSet.ID.value}:eager-api")
        private const val MSG = "eager.api"

        private const val BUILDSCRIPT_CALLEE = "buildscript"
        private const val BUILDSCRIPT_CALL_REPLACEMENT = "plugins"

        private val DOMAIN_OBJECT_CALLEES =
            hashSetOf(
                BUILDSCRIPT_CALL_REPLACEMENT,
                "configurations",
                "sourceSets",
            ) + TASK_CALLEE
        private val DOMAIN_OBJECTS_CALL_REPLACEMENT =
            hashMapOf(
                "all" to "configureEach",
                "withType" to "configureEach",
                "whenObjectAdded" to "configureEach",
            )

        private const val TASK_CALLEE = "tasks"
        private val TASK_CALL_REPLACEMENT =
            hashMapOf(
                "create" to "register",
                "findByName" to "named",
                "getByName" to "named",
                "getByName" to "named",
            ) + DOMAIN_OBJECTS_CALL_REPLACEMENT
    }
}
