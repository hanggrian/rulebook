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
    implementation(libs.guava)

    testImplementation(kotlin("test-junit", libs.versions.kotlin.get()))
    testImplementation(libs.ktlint.test)
    testImplementation(libs.ktlint.ruleset.standard) // for ID comparison
    testImplementation(libs.truth)

    testRuntimeOnly(libs.logback)
}
