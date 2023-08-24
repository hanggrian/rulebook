package com.hendraanggrian.rulebook.codenarc

import com.hendraanggrian.rulebook.codenarc.UseAbstractCollectionNarc.Companion.MSG_LIST
import com.hendraanggrian.rulebook.codenarc.UseAbstractCollectionNarc.Companion.MSG_MAP
import com.hendraanggrian.rulebook.codenarc.UseAbstractCollectionNarc.Companion.MSG_SET
import org.codehaus.groovy.ast.MethodNode
import org.codenarc.rule.AbstractAstVisitor

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/UseAbstractCollection).
 */
class UseAbstractCollectionNarc : RulebookNarc() {
    internal companion object {
        const val MSG_LIST = "use.abstract.collection.list"
        const val MSG_SET = "use.abstract.collection.set"
        const val MSG_MAP = "use.abstract.collection.map"
    }

    override var title: String = "UseAbstractCollection"
    override fun getAstVisitorClass(): Class<*> = UseAbstractCollectionVisitor::class.java
}

class UseAbstractCollectionVisitor : AbstractAstVisitor() {
    override fun visitConstructorOrMethod(node: MethodNode, isConstructor: Boolean) {
        super.visitConstructorOrMethod(node, isConstructor)
        node.parameters.forEach { parameter ->
            // report if explicit collection is found
            val type = parameter.type
            when (type.name) {
                "ArrayList" -> addViolation(parameter, Messages.get(MSG_LIST, type.name))
                "HashSet", "TreeSet" -> addViolation(parameter, Messages.get(MSG_SET, type.name))
                "HashMap", "TreeMap" -> addViolation(parameter, Messages.get(MSG_MAP, type.name))
            }
        }
    }
}
