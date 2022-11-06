plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlinx.kover)
    alias(libs.plugins.dokka)
    alias(libs.plugins.maven.publish)
}

dependencies {
    ktlint(libs.ktlint, ::ktlintAttributes)
    implementation(libs.ktlint.core)
    testImplementation(libs.ktlint.test)
    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.truth)
}
