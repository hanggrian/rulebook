package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.ClassHelper.float_TYPE
import org.codehaus.groovy.ast.ClassHelper.int_TYPE
import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codenarc.rule.AbstractAstVisitor

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#float-literal-tagging)
 */
public class FloatLiteralTaggingRule : Rule() {
    override fun getName(): String = "FloatLiteralTagging"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    internal companion object {
        const val MSG_NUM = "float.literal.tagging.num"
        const val MSG_HEX = "float.literal.tagging.hex"
    }

    public class Visitor : AbstractAstVisitor() {
        override fun visitConstantExpression(node: ConstantExpression) {
            super.visitConstantExpression(node)
            if (!isFirstVisit(node)) {
                return
            }

            // skip other literals
            node.type
                .takeIf { it == int_TYPE || it == float_TYPE }
                ?: return

            // find trailing tag
            val character =
                node.tag
                    ?.takeIf { it.lowercaseChar() == 'f' }
                    ?: return

            // checks for violation
            if (node.type == int_TYPE) {
                character
                    .takeIf { c -> c.isLowerCase() }
                    ?: return
                addViolation(node, Messages[MSG_HEX])
            }
            character
                .takeIf { it.isUpperCase() }
                ?: return
            addViolation(node, Messages[MSG_NUM])
        }

        /**
         * @see org.codenarc.rule.convention.LongLiteralWithLowerCaseLAstVisitor
         */
        private val ConstantExpression.tag: Char? get() {
            val line = sourceCode.lines[lineNumber - 1] ?: return null
            if (line.length <= lastColumnNumber) {
                return line[lastColumnNumber - 2]
            }
            return null
        }
    }
}
