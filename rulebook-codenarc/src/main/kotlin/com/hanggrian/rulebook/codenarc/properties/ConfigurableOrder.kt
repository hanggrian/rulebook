package com.hanggrian.rulebook.codenarc.properties

import com.hanggrian.rulebook.codenarc.splitToList

public interface ConfigurableOrder {
    public val orderSet: LinkedHashSet<String>

    public fun setOrder(order: String) {
        orderSet.clear()
        orderSet += order.splitToList()
    }
}
