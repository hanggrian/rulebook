package com.hendraanggrian.rulebook.checkstyle

import com.hendraanggrian.rulebook.checkstyle.internals.Messages
import com.hendraanggrian.rulebook.checkstyle.internals.actualText
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.IDENT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.TYPE
import com.puppycrawl.tools.checkstyle.api.TokenTypes.VARIABLE_DEF

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#property-idiomatic-naming).
 */
public class PropertyIdiomaticNamingCheck : RulebookCheck() {
    override fun getRequiredTokens(): IntArray = intArrayOf(VARIABLE_DEF)

    override fun visitToken(node: DetailAST) {
        // skip no declaration
        val typeName =
            node.findFirstToken(TYPE)?.actualText
                ?.let {
                    val s = it.substringBefore('<')
                    when {
                        !it.startsWith("java.") -> s
                        else -> s.substringAfterLast('.')
                    }
                } ?: return

        // checks for violation
        val ident =
            node.findFirstToken(IDENT)
                ?.takeIf {
                    typeName.equals(it.text, true) &&
                        (typeName in OBJECT_TYPES || typeName in COLLECTION_TYPES)
                } ?: return
        log(ident, Messages[MSG])
    }

    internal companion object {
        const val MSG = "property.idiomatic.naming"

        private val OBJECT_TYPES =
            setOf(
                "Boolean",
                "boolean",
                "Byte",
                "byte",
                "Char",
                "char",
                "Double",
                "double",
                "Float",
                "float",
                "Int",
                "int",
                "Long",
                "long",
                "Short",
                "short",
                "String",
                "string",
            )
        private val COLLECTION_TYPES =
            setOf(
                "Set",
                "List",
                "Map",
                "Iterable",
                "Collection",
            )
    }
}
