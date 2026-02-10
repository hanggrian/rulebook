package com.hanggrian.rulebook.ktlint.properties

import com.pinterest.ktlint.rule.engine.core.api.editorconfig.CommaSeparatedListValueParser
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.EditorConfigProperty
import org.ec4j.core.model.PropertyType.LowerCasingPropertyType

public val MEMBER_ORDER_PROPERTY: EditorConfigProperty<Set<String>> =
    EditorConfigProperty(
        type =
            LowerCasingPropertyType(
                "rulebook_member_order",
                "The structure of a class body.",
                CommaSeparatedListValueParser(),
            ),
        defaultValue =
            linkedSetOf("property", "initializer", "constructor", "function", "companion"),
        propertyWriter = { it.joinToString() },
    )
