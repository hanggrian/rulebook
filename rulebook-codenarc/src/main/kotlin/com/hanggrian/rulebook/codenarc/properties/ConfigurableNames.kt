package com.hanggrian.rulebook.codenarc.properties

import com.hanggrian.rulebook.codenarc.splitToList

public interface ConfigurableNames {
    public val nameSet: HashSet<String>

    public fun setNames(names: String) {
        nameSet.clear()
        nameSet += names.splitToList()
    }
}
