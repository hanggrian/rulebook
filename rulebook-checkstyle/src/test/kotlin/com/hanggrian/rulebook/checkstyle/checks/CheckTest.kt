package com.hanggrian.rulebook.checkstyle.checks

import com.google.common.truth.Truth.assertThat
import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport
import com.puppycrawl.tools.checkstyle.DefaultConfiguration
import com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck
import kotlin.test.BeforeTest

abstract class CheckTest : AbstractModuleTestSupport() {
    abstract val check: Any

    lateinit var config: DefaultConfiguration

    @BeforeTest
    fun setUp() {
        config = createModuleConfig(check.javaClass)
    }

    final override fun getPackageLocation(): String = check.javaClass.simpleName

    protected fun assertAll(fileNameWithoutExtension: String, vararg expected: String) =
        verify(config, getPath("$fileNameWithoutExtension.java"), *expected)

    protected fun RulebookAstCheck.assertProperties() {
        val requiredTokens = requiredTokens.asList()
        assertThat(defaultTokens).asList().containsExactlyElementsIn(requiredTokens)
        assertThat(acceptableTokens).asList().containsExactlyElementsIn(requiredTokens)
    }

    protected fun RulebookFileCheck.assertProperties() =
        assertThat(fileExtensions).asList().contains(".java")

    protected fun AbstractJavadocCheck.assertProperties() =
        assertThat(defaultJavadocTokens).isNotEmpty()
}
