package com.hanggrian.rulebook.codenarc.rules

import kotlin.test.Test

class LineFeedRuleTest : RuleTest<LineFeedRule>() {
    override fun createRule() = LineFeedRule()

    @Test
    fun `Rule properties`() = rule.assertProperties()

    @Test
    fun `Linux end of line`() = assertNoViolations("package com.example\n")

    @Test
    fun `Windows end of line`() =
        assertSingleViolation(
            "package com.example\r\n",
            1,
            "package com.example",
            "Set line ending to 'LF'.",
        )

    @Test
    fun `Classic macOS end of line`() =
        assertSingleViolation(
            "package com.example\r",
            1,
            "package com.example",
            "Set line ending to 'LF'.",
        )
}
