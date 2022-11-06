plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.kapt)
}

dependencies {
    ktlint(libs.ktlint, ::ktlintAttributes)
    ktlint(project(":$RELEASE_ARTIFACT-ktlint"))
}
