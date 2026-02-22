val releaseArtifact: String by project

plugins {
    kotlin("jvm") version libs.versions.kotlin
    java
    checkstyle
    groovy
    codenarc
}

checkstyle.configFile = projectDir.resolve("custom_checks.xml")

codenarc.configFile = projectDir.resolve("custom_rules.xml")

dependencies {
    checkstyle(project(":$releaseArtifact-checkstyle"))

    codenarc(project(":$releaseArtifact-codenarc"))

    implementation(libs.groovy.all)
}
