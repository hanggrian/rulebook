package com.example.kotlin

class PropertyNameJavaInteroperability(
    private val isPropertyInPrimaryConstructor: Boolean,
    isPropertyInPrimaryConstructor2: Boolean,
) {
    val isPropertyWithType: Boolean = true

    @JvmField
    val propertyWithJvmField: Boolean = true

    val propertyWithJvmName: Boolean
        @JvmName("property")
        get() = true

    fun foo() {
        val variable = false
    }

    companion object {
        val isPropertyInCompanion: Boolean = true
    }
}
