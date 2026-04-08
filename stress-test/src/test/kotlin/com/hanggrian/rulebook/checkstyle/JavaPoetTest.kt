package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test

class JavaPoetTest : AllChecksTest() {
    @Test
    fun com_squareup_javapoet_JavaFile(): Unit =
        verify(
            checker,
            getCode("javapoet/JavaFile.java"),
            "47:59: Put newline before '='.",
            "59:5: Arrange member 'property' before 'static member'.",
            "89:43: Put newline before '='.",
            "132:55: Break each parameter into newline.",
            "165:27: Put newline before '='.",
            "169:42: Put newline before '='.",
            "222:5: Move 'equals' to last.",
            "229:5: Move 'hashCode' to last.",
            "233:5: Move 'toString' to last.",
            "244:19: Put newline before '='.",
            "268:5: Arrange member 'function' before 'static member'.",
        )
}
