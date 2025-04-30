package com.hanggrian.rulebook.codenarc

import org.codenarc.analyzer.SuppressionAnalyzer
import org.codenarc.rule.Rule
import org.codenarc.rule.Violation
import org.codenarc.source.AbstractSourceCode
import org.codenarc.source.CustomCompilerPhaseSourceDecorator
import org.codenarc.source.SourceCode
import org.codenarc.source.SourceString
import org.codenarc.test.AbstractTestCase
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class AllRulesTest : AbstractTestCase() {
    private val rules =
        setOf<Rule>(
            AssignmentWrapRule(),
            BlockTagIndentationRule(),
            BlockTagOrderRule(),
            BlockTagPunctuationRule(),
            BracketsTrimRule(),
            BuiltInFunctionPositionRule(),
            CaseSeparatorRule(),
            ChainCallWrapRule(),
            ClassNameAbbreviationRule(),
            CommentTrimRule(),
            DuplicateBlankLineInCommentRule(),
            EmptyBracesClipRule(),
            EmptyBracketsClipRule(),
            EmptyParenthesesClipRule(),
            EmptyTagsClipRule(),
            IllegalClassFinalNameRule(),
            IllegalVariableNameRule(),
            ImportOrderRule(),
            InnerClassPositionRule(),
            LambdaWrapRule(),
            MemberOrderRule(),
            MemberSeparatorRule(),
            MissingBlankLineBeforeBlockTagsRule(),
            NestedIfElseRule(),
            NumberSuffixForDoubleRule(),
            NumberSuffixForFloatRule(),
            NumberSuffixForIntegerRule(),
            OperatorWrapRule(),
            OverloadFunctionPositionRule(),
            ParameterWrapRule(),
            ParenthesesTrimRule(),
            RedundantDefaultRule(),
            RedundantElseRule(),
            RedundantQualifierRule(),
            ShortBlockCommentClipRule(),
            StatementWrapRule(),
            TagsTrimRule(),
            TodoCommentRule(),
            TrailingCommaInCallRule(),
            UnnecessaryParenthesesInLambdaRule(),
            UnnecessarySwitchRule(),
            UtilityClassDefinitionRule(),
        )
    private val sourceCodeName = "AllRules.groovy"
    private val sourceCodePath = "com/hanggrian/rulebook/codenarc"

    @Test
    fun org_codenarc_CodeNarcRunner() =
        assertViolations(
            javaClass
                .getResource("CodeNarcRunner.groovy")!!
                .readText(),
            violationOf(116, "if (pluginClassNames) {", "Invert 'if' condition."),
            violationOf(149, "if (plugins) {", "Invert 'if' condition."),
            violationOf(167, "* @return a single RuleSet", "End '@return' with a period."),
        )

    @Test
    fun org_grails_gradle_plugin_publishing_GrailsPublishGradlePlugin() =
        assertViolations(
            javaClass
                .getResource("GrailsPublishGradlePlugin.groovy")!!
                .readText(),
            violationOf(
                177,
                "project.tasks.withType(Sign).configureEach { Sign task ->",
                "Put newline before '.'.",
            ),
            violationOf(
                177,
                "project.tasks.withType(Sign).configureEach { Sign task ->",
                "Put newline before '.'.",
            ),
            violationOf(
                177,
                "project.tasks.withType(Sign).configureEach { Sign task ->",
                "Put newline before '.'.",
            ),
            violationOf(
                194,
                "project.rootProject.tasks.withType(InitializeNexusStagingRepository)" +
                    ".configureEach { InitializeNexusStagingRepository task ->",
                "Put newline before '.'.",
            ),
            violationOf(
                194,
                "project.rootProject.tasks.withType(InitializeNexusStagingRepository)" +
                    ".configureEach { InitializeNexusStagingRepository task ->",
                "Put newline before '.'.",
            ),
            violationOf(
                194,
                "project.rootProject.tasks.withType(InitializeNexusStagingRepository)" +
                    ".configureEach { InitializeNexusStagingRepository task ->",
                "Put newline before '.'.",
            ),
            violationOf(
                194,
                "project.rootProject.tasks.withType(InitializeNexusStagingRepository)" +
                    ".configureEach { InitializeNexusStagingRepository task ->",
                "Put newline before '.'.",
            ),
            violationOf(200, "project.rootProject.nexusPublishing {", "Put newline before '.'."),
            violationOf(200, "project.rootProject.nexusPublishing {", "Put newline before '.'."),
            violationOf(
                369,
                "dependencyNodes.findAll { dependencyNode ->",
                "Put newline before '.'.",
            ),
            violationOf(
                375,
                "def resolvedArtifacts = project.configurations.compileClasspath" +
                    ".resolvedConfiguration.resolvedArtifacts +",
                "Break assignment into newline.",
            ),
            violationOf(
                378,
                "resolvedArtifacts += project.configurations.testFixturesCompileClasspath" +
                    ".resolvedConfiguration.resolvedArtifacts +",
                "Put newline after operator '+='.",
            ),
            violationOf(
                381,
                "def managedVersion = resolvedArtifacts.find {",
                "Break assignment into newline.",
            ),
            violationOf(
                381,
                "def managedVersion = resolvedArtifacts.find {",
                "Put newline before '.'.",
            ),
            violationOf(
                384,
                "}?.moduleVersion?.id?.version",
                "Put newline before '.'.",
            ),
            violationOf(
                384,
                "}?.moduleVersion?.id?.version",
                "Put newline before '.'.",
            ),
            violationOf(
                402,
                "extensionContainer.configure(SigningExtension, {",
                "Put trailing comma.",
            ),
            violationOf(
                419,
                "taskContainer.register(\"publish\${GrailsNameUtils" +
                    ".getClassName(defaultClassifier)}\", { Task task ->",
                "Put trailing comma.",
            ),
            violationOf(
                424,
                "if (installTask == null) {",
                "Invert 'if' condition.",
            ),
            violationOf(
                425,
                "taskContainer.register('install', { Task task ->",
                "Put trailing comma.",
            ),
            violationOf(
                433,
                "project.plugins.withId(MAVEN_PUBLISH_PLUGIN_ID) {",
                "Put newline before '.'.",
            ),
            violationOf(
                433,
                "project.plugins.withId(MAVEN_PUBLISH_PLUGIN_ID) {",
                "Put newline before '.'.",
            ),
            violationOf(
                499,
                "project.extensions.configure(JavaPluginExtension) {",
                "Put newline before '.'.",
            ),
            violationOf(
                499,
                "project.extensions.configure(JavaPluginExtension) {",
                "Put newline before '.'.",
            ),
            violationOf(
                505,
                "taskContainer.named('javadocJar', Jar).configure { Jar task ->",
                "Put newline before '.'.",
            ),
            violationOf(
                505,
                "taskContainer.named('javadocJar', Jar).configure { Jar task ->",
                "Put newline before '.'.",
            ),
            violationOf(
                516,
                "taskContainer.named('sourcesJar', Jar).configure { Jar task ->",
                "Put newline before '.'.",
            ),
            violationOf(
                516,
                "taskContainer.named('sourcesJar', Jar).configure { Jar task ->",
                "Put newline before '.'.",
            ),
            violationOf(
                523,
                "project.tasks.register('testSourcesJar', Jar) {",
                "Put newline before '.'.",
            ),
            violationOf(
                523,
                "project.tasks.register('testSourcesJar', Jar) {",
                "Put newline before '.'.",
            ),
            violationOf(
                530,
                "Collection<SourceSet> publishedSources = sourceSets.findAll { SourceSet sourceSet ->",
                "Break assignment into newline.",
            ),
            violationOf(
                534,
                ") && !sourceSet.allSource.isEmpty()",
                "Omit newline before operator '&&'.",
            ),
        )

    @Test
    fun io_seqera_tower_plugin_TowerFusionToken() =
        assertViolations(
            javaClass
                .getResource("TowerFusionToken.groovy")!!
                .readText(),
            violationOf(
                12,
                "import com.google.common.cache.Cache",
                "Remove blank line before directive 'com.google.common.cache.Cache'.",
            ),
            violationOf(
                12,
                "import com.google.common.cache.Cache",
                "Arrange directive 'com.google.common.cache.Cache' " +
                    "before 'java.util.function.Predicate'.",
            ),
            violationOf(
                110,
                "* @param config The Fusion configuration object",
                "End '@param' with a period.",
            ),
            violationOf(
                111,
                "* @return A map of environment variables",
                "End '@return' with a period.",
            ),
            violationOf(
                138,
                "* @return The signed JWT token",
                "Arrange tag '@return' before '@throws'.",
            ),
            violationOf(138, "* @return The signed JWT token", "End '@return' with a period."),
            violationOf(
                175,
                "* @return The new HttpClient instance",
                "End '@return' with a period.",
            ),
            violationOf(
                175,
                "* @return The new HttpClient instance",
                "Add blank line before block tag group.",
            ),
            violationOf(
                178,
                "final builder = HttpClient.newBuilder()",
                "Break assignment into newline.",
            ),
            violationOf(
                178,
                "final builder = HttpClient.newBuilder()",
                "Put newline before '.'.",
            ),
            violationOf(
                196,
                "* @return The new RetryPolicy instance",
                "End '@return' with a period.",
            ),
            violationOf(
                203,
                "final listener = new EventListener<ExecutionAttemptedEvent<HttpResponse<T>>>() {",
                "Break assignment into newline.",
            ),
            violationOf(228, "* @param req The HttpRequest to send", "End '@param' with a period."),
            violationOf(229, "* @return The HttpResponse received", "End '@return' with a period."),
            violationOf(238, "} as CheckedSupplier", "Put trailing comma."),
            violationOf(
                245,
                "* @param req The LicenseTokenRequest object",
                "End '@param' with a period.",
            ),
            violationOf(
                246,
                "* @return The resulting HttpRequest object",
                "End '@return' with a period.",
            ),
            violationOf(
                262,
                "* @param req The LicenseTokenRequest object",
                "End '@param' with a period.",
            ),
            violationOf(263, "* @return The resulting JSON string", "End '@return' with a period."),
            violationOf(
                272,
                "* @param json The String containing the JSON representation of the " +
                    "LicenseTokenResponse object",
                "End '@param' with a period.",
            ),
            violationOf(
                273,
                "* @return The resulting LicenseTokenResponse object",
                "End '@return' with a period.",
            ),
            violationOf(
                284,
                "* @param req The LicenseTokenRequest object",
                "End '@param' with a period.",
            ),
            violationOf(
                285,
                "* @return The LicenseTokenResponse object",
                "End '@return' with a period.",
            ),
        )

    @Test
    fun org_spockframework_gradle_AsciiDocLinkVerifier() =
        assertViolations(
            javaClass
                .getResource("AsciiDocLinkVerifier.groovy")!!
                .readText(),
            violationOf(23, "class AsciiDocLinkVerifier {", "Put 'final' modifier."),
            violationOf(23, "class AsciiDocLinkVerifier {", "Add private constructor."),
            violationOf(38, ".tap {", "Omit newline before '.'."),
            violationOf(58, "def relativeLinkTargets = subject", "Break assignment into newline."),
            violationOf(73, "def result = relativeLinkTargets", "Break assignment into newline."),
            violationOf(97, ".findAll()", "Omit newline before '.'."),
            violationOf(97, ".findAll()", "Put trailing comma."),
            violationOf(107, ".findAll()", "Put trailing comma."),
            violationOf(111, "return result + subject", "Put newline after operator '+'."),
            violationOf(121, ".tap {", "Omit newline before '.'."),
        )

    /**
     * @see org.codenarc.rule.AbstractRuleTestCase.assertViolations
     */
    private fun assertViolations(source: String, vararg violationMaps: Map<String, Any>) {
        val rawViolations = applyRuleTo(source)
        rawViolations.sortBy { it.lineNumber }
        assertEquals(
            rawViolations.size,
            violationMaps.size,
            "Expected ${violationMaps.size} violations\n" +
                "Found ${rawViolations.size}: \n" +
                "    ${rawViolations.joinToString("\n    ")}\n",
        )
        val validKeys =
            listOf("line", "lineNumber", "source", "sourceLineText", "message", "messageText")
        violationMaps.forEachIndexed { index, violationMap ->
            assertTrue(
                violationMap
                    .keys
                    .all { it in validKeys },
                "violationMap keys must be one of $validKeys",
            )
            assertViolation(
                rawViolations[index],
                violationMap["line"]?.toString()?.toInt()
                    ?: violationMap["lineNumber"]!!.toString().toInt(),
                violationMap["source"]?.toString()
                    ?: violationMap["sourceLineText"]!!.toString(),
                violationMap["message"]
                    ?: violationMap["messageText"],
            )
        }
    }

    /**
     * @see org.codenarc.rule.AbstractRuleTestCase.assertViolation
     */
    private fun assertViolation(
        violation: Violation,
        lineNumber: Int,
        sourceLineText: String,
        messageText: Any? = null,
    ) {
        assertTrue(violation.rule in rules)
        assertEquals(
            violation.lineNumber,
            lineNumber,
            "Wrong line number for violation: \n" +
                "$violation\n" +
                "Expected: $lineNumber\n" +
                "Found:    $violation.lineNumber\n",
        )
        if (sourceLineText.isNotBlank()) {
            assertNotNull(violation.sourceLine)
            assertTrue(
                sourceLineText in violation.sourceLine,
                """
                Problem with source text:
                expected to contain:  $sourceLineText
                actual:               $violation.sourceLine
                """.trimIndent(),
            )
        }
        if (messageText == null) {
            return
        }
        assertNotNull(violation.message, "The violation message was null")
        if (messageText is Collection<*>) {
            messageText.forEach {
                assertTrue(
                    it.toString() in violation.message,
                    "text does not contain [$it]",
                )
            }
            return
        }
        assertTrue(
            messageText.toString() in violation.message,
            "Violation on line $lineNumber\n" +
                "Expected message text: [$messageText]\n" +
                "Found message text:    [$violation.message]\n",
        )
    }

    /**
     * @see org.codenarc.rule.AbstractRuleTestCase.applyRuleTo
     */
    private fun applyRuleTo(source: String): MutableList<Violation> {
        val sourceCode = prepareSourceCode(source)
        assertTrue(sourceCode.isValid)
        val violations = rules.flatMap { it.applyTo(sourceCode) }.toMutableList()
        removeSuppressedViolations(sourceCode, violations)
        log("violations=$violations")
        return violations
    }

    /**
     * @see org.codenarc.rule.AbstractRuleTestCase.removeSuppressedViolations
     */
    private fun removeSuppressedViolations(
        sourceCode: SourceCode,
        violations: MutableList<Violation>,
    ) {
        val suppressionAnalyzer = SuppressionAnalyzer(sourceCode)
        violations.removeAll { suppressionAnalyzer.isViolationSuppressed(it) }
    }

    /**
     * @see org.codenarc.rule.AbstractRuleTestCase.prepareSourceCode
     */
    private fun prepareSourceCode(source: String): SourceCode {
        var sourceCode: AbstractSourceCode = SourceString(source, sourceCodePath, sourceCodeName)
        val compilerPhase =
            rules
                .firstOrNull { it.compilerPhase != SourceCode.DEFAULT_COMPILER_PHASE }
                ?.compilerPhase
        if (compilerPhase != null) {
            sourceCode = CustomCompilerPhaseSourceDecorator(sourceCode, compilerPhase)
        }
        return sourceCode
    }
}
