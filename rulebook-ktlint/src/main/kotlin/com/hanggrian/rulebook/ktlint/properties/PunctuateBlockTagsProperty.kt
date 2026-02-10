package com.hanggrian.rulebook.ktlint.properties

import com.pinterest.ktlint.rule.engine.core.api.editorconfig.CommaSeparatedListValueParser
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.EditorConfigProperty
import org.ec4j.core.model.PropertyType.LowerCasingPropertyType

public val PUNCTUATE_BLOCK_TAGS_PROPERTY: EditorConfigProperty<Set<String>> =
    EditorConfigProperty(
        type =
            LowerCasingPropertyType(
                "rulebook_punctuate_block_tags",
                "Block tags that have to end with a period.",
                CommaSeparatedListValueParser(),
            ),
        defaultValue =
            hashSetOf(
                "@constructor",
                "@receiver",
                "@property",
                "@param",
                "@return",
            ),
        propertyWriter = { it.joinToString() },
    )
