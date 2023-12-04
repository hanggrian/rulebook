package com.hendraanggrian.rulebook.ktlint

import com.google.common.truth.Truth.assertThat
import kotlin.test.Test

class RulebookRuleSetTest {
    @Test
    fun `List of rules`() {
        assertThat(
            RulebookRuleSet()
                .getRuleProviders()
                .map { it.createNewRuleInstance().javaClass.kotlin },
        ).containsExactly(
            AcronymCapitalizationRule::class,
            BlockCommentSpacingRule::class,
            BlockTagPunctuationRule::class,
            ConstructorPositionRule::class,
            StaticInitializerPositionRule::class,
            EmptyBlockWrappingRule::class,
            ExceptionThrowingRule::class,
            FileSizingRule::class,
            FunctionExpressionRule::class,
            GenericsNamingRule::class,
            IfStatementNestingRule::class,
            JavaApiUsageRule::class,
            ObjectsComparisonRule::class,
            QualifierRedundancyRule::class,
            StringInterpolationRule::class,
            SwitchCasesSpacingRule::class,
            WordMeaningRule::class,
        )
    }
}
