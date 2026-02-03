package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.rules.MeaninglessWordRule.Companion.MSG_ALL
import com.hanggrian.rulebook.codenarc.rules.MeaninglessWordRule.Companion.MSG_UTIL
import com.hanggrian.rulebook.codenarc.rules.MeaninglessWordRule.Companion.UTILITY_FINAL_NAMES
import com.hanggrian.rulebook.codenarc.splitToList
import org.codehaus.groovy.ast.ClassNode

/** [See detail](https://hanggrian.github.io/rulebook/rules/#meaningless-word) */
public class MeaninglessWordRule : RulebookAstRule() {
    internal var wordList = listOf("Util", "Utility", "Helper", "Manager", "Wrapper")

    public var words: String
        get() = throw UnsupportedOperationException()
        set(value) {
            wordList = value.splitToList()
        }

    override fun getName(): String = "MeaninglessWord"

    override fun getAstVisitorClass(): Class<*> = MeaninglessWordVisitor::class.java

    internal companion object {
        const val MSG_ALL = "meaningless.word.all"
        const val MSG_UTIL = "meaningless.word.util"

        val UTILITY_FINAL_NAMES = setOf("Util", "Utility")
    }
}

public class MeaninglessWordVisitor : RulebookVisitor() {
    override fun visitClassEx(node: ClassNode) {
        super.visitClassEx(node)

        // checks for violation
        val finalName =
            (rule as MeaninglessWordRule)
                .wordList
                .singleOrNull { node.name.endsWith(it) }
                ?: return
        if (finalName in UTILITY_FINAL_NAMES) {
            addViolation(
                node,
                Messages[MSG_UTIL, node.name.substringBefore(finalName) + 's'],
            )
            return
        }
        addViolation(node, Messages[MSG_ALL, finalName])
    }
}
