package com.hanggrian.rulebook.ktlint.internals

import java.lang.ref.WeakReference
import java.text.MessageFormat
import java.util.ResourceBundle

internal object Messages {
    private const val FILENAME = "messages"
    private lateinit var bundleRef: WeakReference<ResourceBundle>

    operator fun get(key: String): String = bundle.getString(key)

    fun get(key: String, vararg args: Any): String =
        MessageFormat(bundle.getString(key)).format(args)

    private val bundle: ResourceBundle
        get() {
            var bundle: ResourceBundle?
            if (::bundleRef.isInitialized) {
                bundle = bundleRef.get()
                if (bundle != null) {
                    return bundle
                }
            }
            bundle = ResourceBundle.getBundle(FILENAME)
            bundleRef = WeakReference(bundle)
            return bundle
        }
}
