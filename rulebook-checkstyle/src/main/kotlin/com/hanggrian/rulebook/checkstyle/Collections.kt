package com.hanggrian.rulebook.checkstyle

/** Creates a two-way mapping for pairing lookup. */
internal fun <E> twoWayMapOf(vararg pairs: Pair<E, E>): Map<E, E> =
    buildMap {
        pairs.forEach {
            put(it.first, it.second)
            put(it.second, it.first)
        }
    }
