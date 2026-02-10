package com.hanggrian.rulebook.ktlint.properties

import com.pinterest.ktlint.rule.engine.core.api.editorconfig.CommaSeparatedListValueParser
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.EditorConfigProperty
import org.ec4j.core.model.PropertyType.LowerCasingPropertyType

public val ILLEGAL_VARIABLE_NAMES_PROPERTY: EditorConfigProperty<Set<String>> =
    EditorConfigProperty(
        type =
            LowerCasingPropertyType(
                "rulebook_illegal_variable_names",
                "A set of banned identifiers.",
                CommaSeparatedListValueParser(),
            ),
        defaultValue =
            hashSetOf(
                "any",
                "boolean",
                "byte",
                "char",
                "double",
                "float",
                "int",
                "long",
                "short",
                "string",
                "many",
                "booleans",
                "bytes",
                "chars",
                "doubles",
                "floats",
                "ints",
                "longs",
                "shorts",
                "strings",
            ),
        propertyWriter = { it.joinToString() },
    )
