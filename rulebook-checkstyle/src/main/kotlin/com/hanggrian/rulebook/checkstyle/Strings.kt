package com.hanggrian.rulebook.checkstyle

/** Interpret XML configuration as a collection. */
internal fun String?.splitToList() = this?.split(',')?.map { it.trim() } ?: emptyList()
