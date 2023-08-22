val RELEASE_ARTIFACT: String by project

plugins {
    kotlin("jvm") version libs.versions.kotlin
    alias(libs.plugins.ktlint)
}

dependencies {
    ktlintRuleset(libs.ktlint)
    ktlintRuleset(project(":$RELEASE_ARTIFACT-ktlint"))
}
