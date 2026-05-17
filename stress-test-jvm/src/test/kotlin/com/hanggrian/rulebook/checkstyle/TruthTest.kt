package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test

class TruthTest : AllChecksTest() {
    @Test
    fun com_google_common_truth_StringSubject(): Unit =
        verify(
            checker,
            getCode("truth/StringSubject.java"),
            "217:9: Invert 'if' condition.",
            "270:15: Lift 'else' and add 'return' in 'if' block.",
            "271:17: Replace 'else' with 'else if' condition.",
            "273:74: Break each parameter into newline.",
            "273:88: Break each parameter into newline.",
            "291:15: Lift 'else' and add 'return' in 'if' block.",
            "292:17: Replace 'else' with 'else if' condition.",
            "294:71: Break each parameter into newline.",
            "294:85: Break each parameter into newline.",
            "310:66: Break each parameter into newline.",
            "310:80: Break each parameter into newline.",
            "325:70: Break each parameter into newline.",
            "325:84: Break each parameter into newline.",
        )
}
