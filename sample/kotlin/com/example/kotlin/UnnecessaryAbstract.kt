package com.example.kotlin

class UnnecessaryAbstract {
    abstract class Foo {
        abstract var asd: Int

        fun bar() {}
    }

    abstract class Baz : Any()
}
