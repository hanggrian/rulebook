package com.hendraanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class EndSentenceWithPeriodChecksTest {
    private val checker =
        prepareChecker(
            EndTagSentenceWithPeriodCheck::class,
            EndCommentSentenceWithPeriodCheck::class,
        )

    @Test
    fun `No sentence`() = assertEquals(0, checker.process(prepareFiles("EndSentenceWithPeriod1")))

    @Test
    fun `Sentences end with a period`() =
        assertEquals(0, checker.process(prepareFiles("EndSentenceWithPeriod2")))

    @Test
    fun `Sentences don't end with a period`() =
        assertEquals(4, checker.process(prepareFiles("EndSentenceWithPeriod3")))

    @Test
    fun `Long sentences`() =
        assertEquals(3, checker.process(prepareFiles("EndSentenceWithPeriod4")))
}
