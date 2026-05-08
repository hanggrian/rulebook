package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.rules.GenericNameRule.Companion.MSG
import com.hanggrian.rulebook.codenarc.rules.GenericNameRule.Companion.PASCAL_CASE_REGEX
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.GenericsType
import org.codehaus.groovy.ast.MethodNode

/** [See detail](https://hanggrian.github.io/rulebook/rules/#generic-name) */
public class GenericNameRule : RulebookAstRule() {
    override fun getName(): String = "GenericName"

    override fun getAstVisitorClass(): Class<*> = GenericNameVisitor::class.java

    internal companion object {
        const val MSG = "generic.name"

        val PASCAL_CASE_REGEX = Regex("^[A-Z](?![A-Z0-9]+$)[a-zA-Z0-9]*$|^[A-Z]\\d*$")
    }
}

public class GenericNameVisitor : RulebookVisitor() {
    override fun visitClassEx(node: ClassNode) {
        if (!isFirstVisit(node)) {
            return
        }
        process(node.genericsTypes)
        super.visitClassEx(node)
    }

    override fun visitConstructorOrMethod(node: MethodNode, isConstructor: Boolean) {
        if (!isFirstVisit(node)) {
            return
        }
        process(node.genericsTypes)
        super.visitConstructorOrMethod(node, isConstructor)
    }

    private fun process(genericTypes: Array<GenericsType>?) {
        // checks for violation
        genericTypes
            ?.filterNot { PASCAL_CASE_REGEX.matches(it.name) }
            ?.forEach { addViolation(it, Messages[MSG]) }
    }
}
