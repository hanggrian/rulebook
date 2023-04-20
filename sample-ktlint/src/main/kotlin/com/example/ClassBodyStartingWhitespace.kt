package com.example

class ShortClass {
    val property = ""
}
class LongClass(
    parameter: String
) {

    val property = ""
}

annotation class ShortAnnotationClass {
    // stub
}
annotation class LongAnnotationClass(
    val parameter: String
) {

    // stub
}

data class ShortDataClass(val parameter: String) {
    val property = ""
}
data class LongDataClass(
    val parameter: String
) {

    val property = ""
}

enum class ShortEnumClass {
    ENTRY
}
enum class LongEnumClass(
    parameter: String
) {

    ENTRY("")
}

sealed class ShortSealedClass {
    val property = ""
}
sealed class LongSealedClass(
    parameter: String
) {

    val property = ""
}

interface ShortInterface {
    val property: String
}
interface LongInterface :
    StubInterface {

    val property: String
}

object ShortObject {
    val property = ""
}
object LongObject :
    StubInterface {

    val property = ""
}

class MyShortCompanionObject {
    companion object {
        val property = ""
    }
}

class MyLongCompanionObject {
    companion object :
        StubInterface {

        val property = ""
    }
}

interface StubInterface
