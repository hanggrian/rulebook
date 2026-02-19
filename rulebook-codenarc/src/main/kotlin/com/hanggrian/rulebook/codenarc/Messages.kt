package com.hanggrian.rulebook.codenarc

import java.lang.ref.WeakReference
import java.text.MessageFormat
import java.util.ResourceBundle

internal object Messages {
    private const val FILENAME = "messages"
    private var bundleRef = WeakReference<ResourceBundle>(null)

    operator fun get(key: String): String = bundle.getString(key)

    /** Returns a formatted message. */
    operator fun get(key: String, vararg args: Any): String =
        MessageFormat(bundle.getString(key)).format(args)

    private val bundle: ResourceBundle
        get() {
            var bundle = bundleRef.get()
            bundle?.let { return it }
            bundle = ResourceBundle.getBundle(FILENAME)
            bundleRef = WeakReference(bundle)
            return bundle
        }
}
