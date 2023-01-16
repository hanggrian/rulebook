package com.example

class NoClass
class ShortClass {
    val property: String = ""
}
class LongClass(
    parameter: String
) {

    val property: String = ""
}

interface NoInterface
interface ShortInterface {
    val property: String
}
interface LongInterface :
    StubInterface1,
    StubInterface2,
    StubInterface3,
    StubInterface4,
    StubInterface5 {

    val property: String
}

object NoObject
object ShortObject {
    val property: String = ""
}
object LongObject :
    StubInterface1,
    StubInterface2,
    StubInterface3,
    StubInterface4,
    StubInterface5,
    StubInterface6 {

    val property: String = ""
}

annotation class NoAnnotationClass
annotation class ShortAnnotationClass {
    // stub
}
annotation class LongAnnotationClass(
    val parameter: String
) {

    // stub
}

data class ShortDataClass(val input: String) {
    val property: String = ""
}
data class LongDataClass(
    val parameter: String
) {

    val property: String = ""
}

enum class NoEnumClass
enum class ShortEnumClass {
    ENTRY
}
enum class LongEnumClass(
    parameter: String
) {

    ENTRY("")
}

sealed class NoSealedClass
sealed class ShortSealedClass {
    val property: String = ""
}
sealed class LongSealedClass(
    parameter: String
) {

    val property: String = ""
}

interface StubInterface1
interface StubInterface2
interface StubInterface3
interface StubInterface4
interface StubInterface5
interface StubInterface6
