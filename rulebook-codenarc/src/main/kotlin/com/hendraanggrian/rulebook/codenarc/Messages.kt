package com.hendraanggrian.rulebook.codenarc

import java.lang.ref.WeakReference
import java.text.MessageFormat
import java.util.*

internal object Messages {
    private const val FILENAME = "messages"
    private lateinit var bundleRef: WeakReference<ResourceBundle>

    operator fun get(key: String): String = when {
        ::bundleRef.isInitialized && bundleRef.get() != null -> bundleRef.get()!!
        else -> ResourceBundle.getBundle(FILENAME).also { bundleRef = WeakReference(it) }
    }.getString(key)

    fun get(key: String, vararg args: String): String = MessageFormat(get(key)).format(args)
}
