package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test

class CheckstyleTest : AllChecksTest() {
    @Test
    fun com_puppycrawl_tools_checkstyle_Checker(): Unit =
        verify(
            checker,
            getCode("checkstyle/Checker.java"),
            "64: Remove blank line after '{'.",
            "72:5: Arrange member 'property' before 'static member'.",
            "75:54: Put newline before '='.",
            "183:9: Invert 'if' condition.",
            "196:44: End '@param' with a period.",
            "205:68: End '@param' with a period.",
            "223:40: Put newline before '='.",
            "223:45: Put newline before '.'.",
            "248:22: Put newline before '.'.",
            "254:13: Omit newline before '.'.",
            "312:85: Break each parameter into newline.",
            "346:48: Break each parameter into newline.",
            "347:50: Break each parameter into newline.",
            "347:64: Break each parameter into newline.",
            "347:68: Break each parameter into newline.",
            "363:48: Break each parameter into newline.",
            "365:31: Break each parameter into newline.",
            "365:35: Break each parameter into newline.",
            "374:41: End '@param' with a period.",
            "386:41: End '@param' with a period.",
            "400:40: End '@param' with a period.",
            "401:52: End '@param' with a period.",
            "409:13: Invert 'if' condition.",
            "425:35: End '@param' with a period.",
            "446:46: Put newline before '='.",
            "484:89: Break each parameter into newline.",
            "513:55: End '@param' with a period.",
            "523:43: End '@param' with a period.",
            "532:43: End '@param' with a period.",
            "551:9: Invert 'if' condition.",
            "568:67: End '@param' with a period.",
            "577:59: End '@param' with a period.",
            "586:61: End '@param' with a period.",
            "596:47: End '@param' with a period.",
            "611:44: End '@param' with a period.",
            "635:44: End '@param' with a period.",
            "655:63: End '@return' with a period.",
            "658:55: Put newline before '='.",
            "659:52: Break each parameter into newline.",
            "660:25: Break each parameter into newline.",
            "664: Remove blank line before '}'.",
        )
}
