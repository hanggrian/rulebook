package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.hendraanggrian.rulebook.ktlint.internals.qualifierName
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.PROPERTY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.TYPE_REFERENCE
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#property-idiomatic-naming).
 */
public class PropertyIdiomaticNamingRule : RulebookRule("property-idiomatic-naming") {
    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit,
    ) {
        // first line of filter
        if (node.elementType != PROPERTY) {
            return
        }

        // skip no declaration
        val typeName =
            node.findChildByType(TYPE_REFERENCE)
                ?.let {
                    val s = it.qualifierName
                    when {
                        !it.text.startsWith("kotlin.") && !it.text.startsWith("java.") -> s
                        else -> s.substringAfterLast('.')
                    }
                } ?: return

        // checks for violation
        val identifier =
            node.findChildByType(IDENTIFIER)
                ?.takeIf {
                    typeName.equals(it.text, true) &&
                        (typeName in OBJECT_TYPES || typeName in COLLECTION_TYPES)
                } ?: return
        emit(identifier.startOffset, Messages[MSG], false)
    }

    internal companion object {
        const val MSG = "property.idiomatic.naming"

        private val OBJECT_TYPES =
            setOf(
                "Boolean",
                "Byte",
                "Char",
                "Double",
                "Float",
                "Int",
                "Long",
                "Short",
                "String",
            )
        private val COLLECTION_TYPES =
            setOf(
                "Set",
                "MutableSet",
                "List",
                "MutableList",
                "Map",
                "MutableMap",
                "Iterable",
                "Collection",
                "Sequence",
            )
    }
}
