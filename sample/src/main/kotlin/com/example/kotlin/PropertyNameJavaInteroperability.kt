package com.example.kotlin

class PropertyNameJavaInteroperability(val isPropertyInPrimaryConstructor: Boolean) {
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
