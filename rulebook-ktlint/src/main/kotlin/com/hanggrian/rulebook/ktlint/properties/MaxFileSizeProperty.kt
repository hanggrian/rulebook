package com.hanggrian.rulebook.ktlint.properties

import com.pinterest.ktlint.rule.engine.core.api.editorconfig.EditorConfigProperty
import org.ec4j.core.model.PropertyType.LowerCasingPropertyType
import org.ec4j.core.model.PropertyType.PropertyValueParser.POSITIVE_INT_VALUE_PARSER

public val MAX_FILE_SIZE_PROPERTY: EditorConfigProperty<Int> =
    EditorConfigProperty(
        type =
            LowerCasingPropertyType(
                "rulebook_max_file_size",
                "Max lines of code that is allowed.",
                POSITIVE_INT_VALUE_PARSER,
            ),
        defaultValue = 1000,
    )
