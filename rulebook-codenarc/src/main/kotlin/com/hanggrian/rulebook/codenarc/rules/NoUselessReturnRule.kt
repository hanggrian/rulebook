package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.rules.NoUselessReturnRule.Companion.MSG
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.ast.stmt.ReturnStatement

/** [See detail](https://hanggrian.github.io/rulebook/rules/#no-useless-return) */
public class NoUselessReturnRule : RulebookAstRule() {
    override fun getName(): String = "NoUselessReturn"

    override fun getAstVisitorClass(): Class<*> = NoUselessReturnVisitor::class.java

    internal companion object {
        const val MSG = "no.useless.return"
    }
}

public class NoUselessReturnVisitor : RulebookVisitor() {
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
                        sourceCode
                            ?.line(it.lineNumber - 1)
                            ?.substringAfter("return")
                            ?.startsWith(" null") == false
                } ?: return super.visitMethodEx(node)
        addViolation(`return`, Messages[MSG])

        super.visitMethodEx(node)
    }
}
