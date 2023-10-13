package com.hendraanggrian.rulebook.codenarc

import com.hendraanggrian.rulebook.codenarc.ReplaceWithAbstractCollectionNarc.Companion.MSG_LIST
import com.hendraanggrian.rulebook.codenarc.ReplaceWithAbstractCollectionNarc.Companion.MSG_MAP
import com.hendraanggrian.rulebook.codenarc.ReplaceWithAbstractCollectionNarc.Companion.MSG_SET
import org.codehaus.groovy.ast.MethodNode
import org.codenarc.rule.AbstractAstVisitor

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/ReplaceWithAbstractCollection).
 */
class ReplaceWithAbstractCollectionNarc : RulebookNarc() {
    internal companion object {
        const val MSG_LIST = "replace.with.abstract.collection.list"
        const val MSG_SET = "replace.with.abstract.collection.set"
        const val MSG_MAP = "replace.with.abstract.collection.map"
    }

    override var title: String = "ReplaceWithAbstractCollection"
    override fun getAstVisitorClass(): Class<*> = ReplaceWithAbstractCollectionVisitor::class.java
}

class ReplaceWithAbstractCollectionVisitor : AbstractAstVisitor() {
    override fun visitConstructorOrMethod(node: MethodNode, isConstructor: Boolean) {
        // report if explicit collection is found
        node.parameters.forEach { parameter ->
            val type = parameter.type
            when (type.name) {
                "ArrayList" -> addViolation(parameter, Messages[MSG_LIST])
                "HashSet", "TreeSet" -> addViolation(parameter, Messages[MSG_SET])
                "HashMap", "TreeMap" -> addViolation(parameter, Messages[MSG_MAP])
            }
        }
        super.visitConstructorOrMethod(node, isConstructor)
    }
}
