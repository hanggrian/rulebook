plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlinx.kover)
    alias(libs.plugins.dokka)
    alias(libs.plugins.maven.publish)
}

dependencies {
    ktlint(libs.ktlint, ::ktlintAttributes)
    ktlint(project(":$RELEASE_ARTIFACT-ktlint"))
    implementation(libs.checkstyle)
    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.truth)
}
