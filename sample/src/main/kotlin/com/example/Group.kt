package com.example

/**
 * A group of *members*.
 *
 * This class has no
 * useful `logic`; it's just a
 * documentation [example](http://some.url/).
 *
 * @param T the type of a member in this group.
 * @property name the name of this group.
 * @constructor Creates an empty group.
 */
class Group<T>(val name: String) {
    /**
     * Adds a [member] to this group.
     * @receiver any objects.
     * @return the new size of the group.
     */
    fun Any.add(member: T) { }
}
