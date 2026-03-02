package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.rules.UnnecessaryReturnRule.Companion.MSG
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.ast.stmt.ReturnStatement

/** [See detail](https://hanggrian.github.io/rulebook/rules/#unnecessary-return) */
public class UnnecessaryReturnRule : RulebookAstRule() {
    override fun getName(): String = "UnnecessaryReturn"

    override fun getAstVisitorClass(): Class<*> = UnnecessaryReturnVisitor::class.java

    internal companion object {
        const val MSG = "unnecessary.return"
    }
}

public class UnnecessaryReturnVisitor : RulebookVisitor() {
    override fun visitMethodEx(node: MethodNode) {
        if (!isFirstVisit(node)) {
            return
        }

        // checks for violation
        val `return` =
            (node.code as? BlockStatement)
                ?.statements
                ?.lastOrNull()
                ?.takeIf {
                    (it as? ReturnStatement)?.isReturningNullOrVoid == true &&
                        !sourceCode
                            .line(it.lineNumber - 1)
                            .substringAfter("return")
                            .startsWith(" null")
                } ?: return
        addViolation(`return`, Messages[MSG])

        super.visitMethodEx(node)
    }
}
