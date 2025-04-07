package com.hanggrian.rulebook.checkstyle

import com.google.common.truth.Truth.assertThat
import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport
import com.puppycrawl.tools.checkstyle.DefaultConfiguration
import com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck
import kotlin.test.BeforeTest

abstract class CheckTest : AbstractModuleTestSupport() {
    abstract val check: Any

    private lateinit var config: DefaultConfiguration

    @BeforeTest
    fun config() {
        config = createModuleConfig(check.javaClass)
    }

    override fun getPackageLocation(): String = check.javaClass.simpleName

    fun assertAll(fileNameWithoutExtension: String, vararg expected: String) =
        verify(config, getPath("$fileNameWithoutExtension.java"), *expected)

    fun RulebookAstCheck.assertProperties() {
        val requiredTokens = requiredTokens.asList()
        assertThat(defaultTokens).asList().containsExactlyElementsIn(requiredTokens)
        assertThat(acceptableTokens).asList().containsExactlyElementsIn(requiredTokens)
    }

    fun RulebookFileCheck.assertProperties() = assertThat(fileExtensions).asList().contains(".java")

    fun AbstractJavadocCheck.assertProperties() = assertThat(defaultJavadocTokens).isNotEmpty()
}
