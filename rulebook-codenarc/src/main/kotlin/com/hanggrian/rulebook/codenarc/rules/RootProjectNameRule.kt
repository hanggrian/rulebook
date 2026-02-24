package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.rules.RootProjectNameRule.Companion.BANNED_CHARACTERS
import com.hanggrian.rulebook.codenarc.rules.RootProjectNameRule.Companion.MSG_DEFAULT
import com.hanggrian.rulebook.codenarc.rules.RootProjectNameRule.Companion.MSG_SPECIAL
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.expr.BinaryExpression
import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codehaus.groovy.ast.expr.PropertyExpression
import org.codehaus.groovy.ast.expr.VariableExpression
import java.io.File.separator

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
        sourceCode.name?.substringAfterLast(separator) == "settings.gradle"

    override fun visitClassComplete(node: ClassNode) {
        // only target settings.gradle
        if (isFirstVisit(node) && isScript() && !hasRootProjectName) {
            addViolation(node, Messages[MSG_DEFAULT])
        }

        super.visitClassComplete(node)
    }

    override fun visitBinaryExpression(node: BinaryExpression) {
        if (!isFirstVisit(node) || !isScript()) {
            return
        }

        node
            .leftExpression
            .takeIf {
                it !is PropertyExpression ||
                    (it.objectExpression as? VariableExpression)?.name != "rootProject" ||
                    (it.property as? ConstantExpression)?.value != "name"
            }?.let { return super.visitBinaryExpression(node) }
        hasRootProjectName = true
        (node.rightExpression as? ConstantExpression)
            ?.value
            ?.takeIf { o -> o.toString().any { it in BANNED_CHARACTERS } }
            ?: return super.visitBinaryExpression(node)
        addViolation(node.rightExpression, Messages[MSG_SPECIAL])

        super.visitBinaryExpression(node)
    }
}
