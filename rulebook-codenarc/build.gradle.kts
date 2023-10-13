val RELEASE_ARTIFACT: String by project

plugins {
    groovy
    kotlin("jvm") version libs.versions.kotlin
    alias(libs.plugins.kotlinx.kover)
    alias(libs.plugins.dokka)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.maven.publish)
}

dependencies {
    ktlintRuleset(libs.ktlint)
    ktlintRuleset(project(":$RELEASE_ARTIFACT-ktlint"))

    implementation(libs.groovy)
    implementation(libs.codenarc)

    testImplementation(kotlin("test-junit5", libs.versions.kotlin.get()))
    testImplementation(libs.ktlint.test)
    testImplementation(libs.truth)
}

tasks.withType<Test> {
    useJUnitPlatform()
    maxParallelForks = 2
    jvmArgs("-Duser.language=en -Duser.country=US")
}
