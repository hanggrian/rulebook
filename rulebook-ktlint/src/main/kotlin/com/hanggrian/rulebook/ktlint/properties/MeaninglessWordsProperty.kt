package com.hanggrian.rulebook.ktlint.properties

import com.pinterest.ktlint.rule.engine.core.api.editorconfig.CommaSeparatedListValueParser
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.EditorConfigProperty
import org.ec4j.core.model.PropertyType.LowerCasingPropertyType

public val MEANINGLESS_WORDS_PROPERTY: EditorConfigProperty<Set<String>> =
    EditorConfigProperty(
        type =
            LowerCasingPropertyType(
                "rulebook_meaningless_words",
                "A set of banned names.",
                CommaSeparatedListValueParser(),
            ),
        defaultValue = hashSetOf("Util", "Utility", "Helper", "Manager", "Wrapper"),
        propertyWriter = { it.joinToString() },
    )
