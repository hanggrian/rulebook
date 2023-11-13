package com.hendraanggrian.rulebook.ktlint.internals

import com.pinterest.ktlint.rule.engine.core.api.editorconfig.EditorConfigProperty
import org.ec4j.core.model.PropertyType

val VARIANT_PROPERTY_TYPE: PropertyType.LowerCasingPropertyType<VariantValue> =
    PropertyType.LowerCasingPropertyType(
        "ktlint_rulebook_variant",
        "The library variant have some extra rules",
        PropertyType.PropertyValueParser.EnumValueParser(VariantValue::class.java),
        setOf("application", "library"),
    )

val VARIANT_PROPERTY: EditorConfigProperty<VariantValue> =
    EditorConfigProperty(
        type = VARIANT_PROPERTY_TYPE,
        defaultValue = VariantValue.application,
    )

@Suppress("EnumEntryName")
enum class VariantValue {
    application,
    library,
}
