package com.hanggrian.rulebook.codenarc

import kotlin.test.Test

class GrailsTest : AllRulesTest() {
    @Test
    fun org_grails_gradle_plugin_publishing_GrailsPublishGradlePlugin() =
        assertViolations(
            getCode("grails/GrailsPublishGradlePlugin.groovy"),
            violationOf(
                59,
                "String getErrorMessage(String missingSetting) {",
                "Arrange member 'function' before 'static member'.",
            ),
            violationOf(
                86,
                "By default snapshotPublishType is set to MAVEN_PUBLISH and releasePublishType " +
                    "is set to NEXUS_PUBLISH.  These can be overridden by setting the associated " +
                    "property.",
                "Remove consecutive whitespace.",
            ),
            violationOf(
                111,
                "if (project.extensions.findByName('grailsPublish') == null) {",
                "Replace equality with 'is()'.",
            ),
            violationOf(
                130,
                "if (System.getenv(ENVIRONMENT_VARIABLE_BASED_RELEASE) != null) {",
                "Replace equality with 'is()'.",
            ),
            violationOf(
                270,
                "if (gpe != null) {",
                "Replace equality with 'is()'.",
            ),
            violationOf(
                283,
                "if (license != null) {",
                "Replace equality with 'is()'.",
            ),
            violationOf(
                285,
                "if (concreteLicense != null) {",
                "Replace equality with 'is()'.",
            ),
            violationOf(
                369,
                "dependencyNodes.findAll { dependencyNode ->",
                "Put newline before '.'.",
            ),
            violationOf(
                371,
                "return versionNodes.size() == 0 || " +
                    "(versionNodes.first() as Node).text().isEmpty()",
                "Replace equality with 'is()'.",
            ),
            violationOf(
                375,
                "def resolvedArtifacts = project.configurations.compileClasspath" +
                    ".resolvedConfiguration.resolvedArtifacts +",
                "Put newline before '='.",
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
                "Put newline before '='.",
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
                418,
                "if (publishToSonatypeTask != null && " +
                    "taskContainer.findByName(" +
                    "\"publish\${GrailsNameUtils.getClassName(defaultClassifier)}\"" +
                    ") == null) {",
                "Replace equality with 'is()'.",
            ),
            violationOf(
                418,
                "if (publishToSonatypeTask != null && " +
                    "taskContainer.findByName(" +
                    "\"publish\${GrailsNameUtils.getClassName(defaultClassifier)}\"" +
                    ") == null) {",
                "Replace equality with 'is()'.",
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
                424,
                "if (installTask == null) {",
                "Replace equality with 'is()'.",
            ),
            violationOf(
                425,
                "taskContainer.register('install', { Task task ->",
                "Put trailing comma.",
            ),
            violationOf(
                473,
                "source    : pluginXml,",
                "Remove consecutive whitespace.",
            ),
            violationOf(
                530,
                "Collection<SourceSet> publishedSources = " +
                    "sourceSets.findAll { SourceSet sourceSet ->",
                "Put newline before '='.",
            ),
            violationOf(
                534,
                ") && !sourceSet.allSource.isEmpty()",
                "Omit newline before operator '&&'.",
            ),
        )
}
