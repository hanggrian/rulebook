plugins {
    kotlin("jvm") version libs.versions.kotlin
    alias(libs.plugins.kotlinx.kover)
    alias(libs.plugins.dokka)
    alias(libs.plugins.maven.publish)
}

dependencies {
    ktlint(libs.ktlint, ::configureKtlint)
    ktlint(project(":$RELEASE_ARTIFACT-ktlint"))
    implementation(libs.checkstyle)
    testImplementation(kotlin("test-junit", libs.versions.kotlin.get()))
    testImplementation(libs.truth)
}
