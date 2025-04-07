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
    val rules =
        setOf<Rule>(
            AssignmentWrapRule(),
            BuiltInFunctionPositionRule(),
            CaseSeparatorRule(),
            ClassNameAcronymRule(),
            CommentTrimRule(),
            DuplicateBlankLineInCommentRule(),
            IllegalClassFinalNameRule(),
            IllegalVariableNameRule(),
            ImportOrderRule(),
            InnerClassPositionRule(),
            MemberOrderRule(),
            MemberSeparatorRule(),
            NestedIfElseRule(),
            NumberSuffixForDoubleRule(),
            NumberSuffixForFloatRule(),
            NumberSuffixForIntegerRule(),
            OperatorWrapRule(),
            OverloadFunctionPositionRule(),
            ParameterWrapRule(),
            RedundantDefaultRule(),
            RedundantElseRule(),
            RedundantQualifierRule(),
            StatementWrapRule(),
            TrailingCommaInCallRule(),
            UnnecessaryParenthesesInLambdaRule(),
            UnnecessarySwitchRule(),
            UtilityClassDefinitionRule(),
        )
    private val sourceCodeName = "All.groovy"
    private val sourceCodePath = "com/hanggrian/rulebook/codenarc/all"

    @Test
    fun org_codenarc_CodeNarcRunner() =
        assertViolations(
            javaClass
                .getResource("CodeNarcRunner.groovy")!!
                .readText(),
            violationOf(116, "if (pluginClassNames) {", "Invert 'if' condition."),
            violationOf(149, "if (plugins) {", "Invert 'if' condition."),
        )

    @Test
    fun org_grails_gradle_plugin_core_GrailsGradlePlugin() =
        assertViolations(
            javaClass
                .getResource("GrailsGradlePlugin.groovy")!!
                .readText(),
            violationOf(
                68,
                "import javax.inject.Inject",
                "Remove blank line before directive 'javax.inject.Inject'.",
            ),
            violationOf(
                68,
                "import javax.inject.Inject",
                "Arrange directive 'javax.inject.Inject' " +
                    "before 'org.springframework.boot.gradle.plugin.SpringBootPlugin'",
            ),
            violationOf(
                178,
                "if (!project.tasks.findByName('configureGroovyCompiler')) {",
                "Invert 'if' condition.",
            ),
            violationOf(
                198,
                "List<String> scripts = " +
                    "sourceConfigFiles.get().asFile.listFiles({ File dir, String name ->",
                "Break assignment into newline.",
            ),
            violationOf(200, "} as FilenameFilter).collect { it.text }", "Put trailing comma."),
            violationOf(
                260,
                "if (!slf4jPreventExclusion || slf4jPreventExclusion != 'true') {",
                "Invert 'if' condition.",
            ),
            violationOf(
                268,
                "if (project.configurations.findByName(PROFILE_CONFIGURATION) == null) {",
                "Invert 'if' condition.",
            ),
            violationOf(
                321,
                "if (project.tasks.findByName(\"buildProperties\") == null) {",
                "Invert 'if' condition.",
            ),
            violationOf(352, "if (micronautVersion) {", "Invert 'if' condition."),
            violationOf(361, "} as Action<DependencyResolveDetails>)", "Put trailing comma."),
            violationOf(362, "} as Action<Configuration>)", "Put trailing comma."),
            violationOf(369, "if (groovyVersion) {", "Invert 'if' condition."),
            violationOf(
                437,
                "if (project.tasks.findByName(taskName) == null) {",
                "Invert 'if' condition.",
            ),
            violationOf(
                507,
                "if (project.extensions.findByName('assets')) {",
                "Invert 'if' condition.",
            ),
            violationOf(
                518,
                "def map = System.properties.findAll { entry ->",
                "Break assignment into newline.",
            ),
            violationOf(523, "if (value) {", "Invert 'if' condition."),
            violationOf(
                561,
                "if (project.configurations.findByName(\"console\") == null) {",
                "Invert 'if' condition.",
            ),
            violationOf(606, "if (environment.isReloadEnabled()) {", "Invert 'if' condition."),
            violationOf(
                722,
                "if (project.tasks.findByName(\"runScript\") == null) {",
                "Invert 'if' condition.",
            ),
            violationOf(
                735,
                "if (project.tasks.findByName(\"runCommand\") == null) {",
                "Invert 'if' condition.",
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
                178,
                "final builder = HttpClient.newBuilder()",
                "Break assignment into newline.",
            ),
            violationOf(
                203,
                "final listener = new EventListener<ExecutionAttemptedEvent<HttpResponse<T>>>() {",
                "Break assignment into newline.",
            ),
            violationOf(238, "} as CheckedSupplier", "Put trailing comma."),
        )

    @Test
    fun spock_util_SourceToAstNodeAndSourceTranspiler() =
        assertViolations(
            javaClass
                .getResource("SourceToAstNodeAndSourceTranspiler.groovy")!!
                .readText(),
            violationOf(
                13,
                "import java.lang.reflect.Modifier",
                "Remove blank line before directive 'java.lang.reflect.Modifier'.",
            ),
            violationOf(
                13,
                "import java.lang.reflect.Modifier",
                "Arrange directive 'java.lang.reflect.Modifier' " +
                    "before 'org.spockframework.compat.groovy2.GroovyCodeVisitorCompat'.",
            ),
            violationOf(
                16,
                "import groovy.transform.*",
                "Remove blank line before directive 'groovy.transform.*'.",
            ),
            violationOf(
                16,
                "import groovy.transform.*",
                "Arrange directive 'groovy.transform.*' before 'java.security.CodeSource'.",
            ),
            violationOf(426, "if (packageNode) {", "Invert 'if' condition."),
            violationOf(445, "if (node) {", "Invert 'if' condition."),
            violationOf(536, "if (generics != null) {", "Invert 'if' condition."),
            violationOf(677, "if (node?.members) {", "Invert 'if' condition."),
            violationOf(
                875,
                "if (!(expression.rightExpression instanceof EmptyExpression) || " +
                    "expression.operation.type != Types.ASSIGN) {",
                "Invert 'if' condition.",
            ),
            violationOf(961, "} else {", "Lift 'else' and add 'return' in 'if' block."),
            violationOf(998, "} else {", "Lift 'else' and add 'return' in 'if' block."),
            violationOf(1013, "} else {", "Lift 'else' and add 'return' in 'if' block."),
            violationOf(1026, "} else {", "Lift 'else' and add 'return' in 'if' block."),
            violationOf(1096, "} else {", "Lift 'else' and add 'return' in 'if' block."),
            violationOf(1112, "} else {", "Lift 'else' and add 'return' in 'if' block."),
            violationOf(1161, "} else {", "Lift 'else' and add 'return' in 'if' block."),
            violationOf(
                1328,
                "if (expression?.expressions != null) { // print array initializer",
                "Invert 'if' condition.",
            ),
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
