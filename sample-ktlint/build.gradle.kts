val releaseArtifact: String by project

plugins {
    kotlin("jvm") version libs.versions.kotlin
    alias(libs.plugins.ktlint)
}

dependencies {
    ktlintRuleset(project(":$releaseArtifact-ktlint"))
}
