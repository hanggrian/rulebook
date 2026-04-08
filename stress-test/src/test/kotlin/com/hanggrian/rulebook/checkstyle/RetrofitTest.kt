package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test

class RetrofitTest : AllChecksTest() {
    @Test
    fun retrofit2_RequestBuilder(): Unit =
        verify(
            checker,
            getCode("retrofit/RequestBuilder.java"),
            "52:5: Arrange member 'property' before 'static member'.",
            "156:21: Break each parameter into newline.",
            "156:35: Break each parameter into newline.",
            "156:44: Break each parameter into newline.",
            "156:55: Break each parameter into newline.",
            "186:5: Arrange member 'function' before 'static member'.",
            "200:11: Lift 'else' and add 'return' in 'if' block.",
        )
}
