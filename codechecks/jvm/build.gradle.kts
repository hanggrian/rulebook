val releaseArtifact: String by project

plugins {
    kotlin("jvm") version libs.versions.kotlin
}

dependencies {
    implementation(project(":$releaseArtifact-ktlint"))
    implementation(libs.ktlint.rule.engine.core)
    implementation(libs.ktlint.cli.ruleset.core)
}
