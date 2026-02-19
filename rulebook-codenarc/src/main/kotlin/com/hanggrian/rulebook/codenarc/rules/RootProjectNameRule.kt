package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.rules.RootProjectNameRule.Companion.MSG_DEFAULT
import com.hanggrian.rulebook.codenarc.rules.RootProjectNameRule.Companion.MSG_SPECIAL
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.expr.BinaryExpression
import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codehaus.groovy.ast.expr.PropertyExpression
import org.codehaus.groovy.ast.expr.VariableExpression
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.ast.stmt.ExpressionStatement
import java.io.File

/** [See detail](https://hanggrian.github.io/rulebook/rules/#root-project-name) */
public class RootProjectNameRule : RulebookAstRule() {
    override fun getName(): String = "RootProjectName"

    override fun getAstVisitorClass(): Class<*> = RootProjectNameVisitor::class.java

    internal companion object {
        const val MSG_DEFAULT = "root.project.name.default"
        const val MSG_SPECIAL = "root.project.name.special"

        val BANNED_CHARACTERS =
            hashSetOf(
                ' ',
                ':',
                '/',
                '\\',
                // Gradle
                '[',
                ']',
                '<',
                '>',
                '"',
                '?',
                '*',
                '|',
                // Shell
                '$',
                '(',
                ')',
                '{',
                '}',
                '&',
                '!',
                ';',
                '\'',
                '`',
            )
    }
}

public class RootProjectNameVisitor : RulebookVisitor() {
    private var hasRootProjectName = false

    override fun isScript(): Boolean =
        sourceCode.name?.substringAfterLast(File.separator) == "settings.gradle"

    override fun visitClassComplete(node: ClassNode) {
        super.visitClassComplete(node)

        // only target settings.gradle
        if (isScript() && !hasRootProjectName) {
            addViolation(node, Messages[MSG_DEFAULT])
        }
    }

    override fun visitBinaryExpression(node: BinaryExpression) {
        super.visitBinaryExpression(node)
        if (!isScript()) {
            return
        }

        val left = node.leftExpression
        if (left !is PropertyExpression ||
            (left.objectExpression as? VariableExpression)?.name != "rootProject" ||
            (left.property as? ConstantExpression)?.value != "name"
        ) {
            return
        }
        hasRootProjectName = true
        val value = (node.rightExpression as? ConstantExpression)?.value as? String ?: return
        if (value.any { it in RootProjectNameRule.BANNED_CHARACTERS }) {
            addViolation(node.rightExpression, Messages[MSG_SPECIAL])
        }
    }

    public fun visitBlockStatement2(node: BlockStatement) {
        super.visitBlockStatement(node)
        if (!isScript() || node.statements.isEmpty()) {
            return
        }

        // find root project name assignment
        val assignment =
            node.statements
                .filterIsInstance<ExpressionStatement>()
                .mapNotNull { it.expression as? BinaryExpression }
                .firstOrNull { expr ->
                    val left = expr.leftExpression
                    left is PropertyExpression &&
                        (left.objectExpression as? VariableExpression)?.name == "rootProject" &&
                        (left.property as? ConstantExpression)?.value == "name"
                }

        // checks for violation
        if (assignment == null) {
            addViolation(node, Messages[MSG_DEFAULT])
            return
        }
        val value = (assignment.rightExpression as? ConstantExpression)?.value as? String ?: return
        if (value.any { it in RootProjectNameRule.BANNED_CHARACTERS }) {
            addViolation(assignment.rightExpression, Messages[MSG_SPECIAL])
        }
    }
}
