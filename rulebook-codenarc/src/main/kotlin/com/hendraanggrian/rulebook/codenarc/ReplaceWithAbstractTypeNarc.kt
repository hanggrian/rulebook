package com.hendraanggrian.rulebook.codenarc

import com.hendraanggrian.rulebook.codenarc.ReplaceWithAbstractTypeNarc.Companion.MSG
import org.codehaus.groovy.ast.MethodNode
import org.codenarc.rule.AbstractAstVisitor

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/ReplaceWithAbstractType).
 */
class ReplaceWithAbstractTypeNarc : RulebookNarc() {
    internal companion object {
        const val MSG = "replace.with.abstract.type"
    }

    override var title: String = "ReplaceWithAbstractType"
    override fun getAstVisitorClass(): Class<*> = ReplaceWithAbstractTypeVisitor::class.java
}

class ReplaceWithAbstractTypeVisitor : AbstractAstVisitor() {
    override fun visitConstructorOrMethod(node: MethodNode, isConstructor: Boolean) {
        // report if explicit collection is found
        node.parameters.forEach { parameter ->
            val type = parameter.type
            when (type.name) {
                "ArrayList" -> addViolation(parameter, Messages.get(MSG, "List"))
                "HashSet", "TreeSet" -> addViolation(parameter, Messages.get(MSG, "Set"))
                "HashMap", "TreeMap" -> addViolation(parameter, Messages.get(MSG, "Map"))
                "FileInputStream", "ByteArrayInputStream" ->
                    addViolation(parameter, Messages.get(MSG, "InputStream"))
                "FileOutputStream", "ByteArrayOutputStream" ->
                    addViolation(parameter, Messages.get(MSG, "OutputStream"))
            }
        }
        super.visitConstructorOrMethod(node, isConstructor)
    }
}
