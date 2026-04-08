package com.hanggrian.rulebook

interface JvmBasedTest {
    fun getCode(file: String) = javaClass.getResource(file)!!.readText()
}
