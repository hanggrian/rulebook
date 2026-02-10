package com.hanggrian.rulebook.checkstyle.properties

public interface ConfigurableOrder {
    public val orderSet: LinkedHashSet<String>

    public fun setOrder(vararg order: String) {
        orderSet.clear()
        orderSet += order
    }
}
