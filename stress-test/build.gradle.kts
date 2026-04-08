val releaseArtifact: String by project

plugins {
    kotlin("jvm") version libs.versions.kotlin
}

dependencies {
    testImplementation(libs.checkstyle)
    testImplementation(libs.codenarc)
    testImplementation(project(":testing"))
    testImplementation(project(":$releaseArtifact-checkstyle"))
    testImplementation(project(":$releaseArtifact-codenarc"))
    testImplementation(project(":$releaseArtifact-ktlint"))
    testImplementation(kotlin("test-junit5", libs.versions.kotlin.get()))
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.bundles.junit5)
    testImplementation(libs.ktlint.test)
    testImplementation(libs.ktlint.ruleset.standard)

    testRuntimeOnly(libs.junit.platform.launcher)
    testRuntimeOnly(libs.logback)
}
