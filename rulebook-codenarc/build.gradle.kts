val releaseArtifact: String by project

plugins {
    groovy
    kotlin("jvm") version libs.versions.kotlin
    alias(libs.plugins.kotlinx.kover)
    alias(libs.plugins.dokka)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.maven.publish)
}

kotlin.explicitApi()

dependencies {
    ktlintRuleset(project(":$releaseArtifact-ktlint"))

    api(libs.codenarc)

    implementation(libs.groovy)

    testImplementation(kotlin("test-junit5", libs.versions.kotlin.get()))
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.bundles.junit5)

    testRuntimeOnly(libs.junit.platform.launcher)
}

tasks.test {
    maxParallelForks = 2
    jvmArgs("-Duser.language=en -Duser.country=US")
}
