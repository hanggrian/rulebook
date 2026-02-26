package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import org.codenarc.rule.Violation
import org.codenarc.source.SourceCode

/** [See detail](https://hanggrian.github.io/rulebook/rules/#modifier-order) */
public class ModifierOrderRule : RulebookFileRule() {
    override fun getName(): String = "ModifierOrder"

    override fun applyTo(code: SourceCode, violations: MutableList<Violation>) {
        for ((index, line) in code.lines.withIndex()) {
            // checks for violation
            val modifiers =
                line
                    .trim()
                    .split(Regex("\\s+"))
                    .filter { it in ORDER_SET }
                    .takeIf { it.size > 1 }
                    ?: continue
            val sortedModifiers =
                modifiers
                    .sortedBy { ORDER_SET.indexOf(it) }
                    .takeUnless { modifiers == it }
                    ?: continue
            violations +=
                createViolation(
                    index + 1,
                    line,
                    Messages[MSG, sortedModifiers.joinToString(" ")],
                )
        }
    }

    internal companion object {
        const val MSG = "modifier.order"

        private val ORDER_SET =
            linkedSetOf(
                "public",
                "protected",
                "private",
                "abstract",
                "default",
                "static",
                "sealed",
                "non-sealed",
                "final",
                "transient",
                "volatile",
                "synchronized",
                "native",
                "strictfp",
            )
    }
}
