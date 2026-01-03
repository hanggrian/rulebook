package com.hanggrian.rulebook.checkstyle

import java.lang.ref.WeakReference
import java.text.MessageFormat
import java.util.ResourceBundle

internal object Messages {
    private const val FILENAME = "messages"
    private var bundleRef = WeakReference<ResourceBundle>(null)

    operator fun get(key: String): String = bundle.getString(key)

    /**
     * Returns a formatted message. The quotes won't appear in Checkstyle HTML reports without
     * extra quotes.
     */
    operator fun get(key: String, vararg args: Any): String =
        MessageFormat(bundle.getString(key)).format(args.map { "'$it'" }.toTypedArray())

    private val bundle: ResourceBundle
        get() {
            var bundle = bundleRef.get()
            if (bundle != null) {
                return bundle
            }
            bundle = ResourceBundle.getBundle(FILENAME)
            bundleRef = WeakReference(bundle)
            return bundle
        }
}
