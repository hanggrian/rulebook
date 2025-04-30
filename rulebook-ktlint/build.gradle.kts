val releaseArtifact: String by project

plugins {
    kotlin("jvm") version libs.versions.kotlin
    alias(libs.plugins.kotlinx.kover)
    alias(libs.plugins.dokka)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.maven.publish)
}

kotlin.explicitApi()

dependencies {
    implementation(libs.ktlint.rule.engine.core)
    implementation(libs.ktlint.cli.ruleset.core)

    testImplementation(kotlin("test-junit5", libs.versions.kotlin.get()))
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.truth)
    testImplementation(libs.ktlint.test)
    testImplementation(libs.ktlint.ruleset.standard) // for ID comparison

    testRuntimeOnly(libs.junit.platform.launcher)
    testRuntimeOnly(libs.logback)
}
