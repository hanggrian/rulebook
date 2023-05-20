plugins {
    kotlin("jvm") version libs.versions.kotlin
    alias(libs.plugins.kotlinx.kover)
    alias(libs.plugins.dokka)
    alias(libs.plugins.maven.publish)
}

dependencies {
    ktlint(libs.ktlint, ::configureKtlint)
    implementation(libs.ktlint.core)
    implementation(libs.ktlint.rule.engine.core)
    implementation(libs.ktlint.cli.ruleset.core)
    testImplementation(kotlin("test-junit", libs.versions.kotlin.get()))
    testImplementation(libs.ktlint.test)
    testImplementation(libs.truth)
}
