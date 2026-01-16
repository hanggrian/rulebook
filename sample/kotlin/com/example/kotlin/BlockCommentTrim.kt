package com.example.kotlin

class BlockCommentTrim {
    /**
     * Foo is awesome.
     *
     * @return a number.
     */
    fun foo(): Int = 0

    /** Foo is awesome. */
    fun bar(): Int = 0

    /** */
    fun <T> baz(t: T, items: List<T>) {}
}
