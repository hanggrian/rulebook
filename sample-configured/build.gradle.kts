val releaseArtifact: String by project

plugins {
    kotlin("jvm") version libs.versions.kotlin
    alias(libs.plugins.ktlint)
    java
    checkstyle
    groovy
    codenarc
}

checkstyle {
    toolVersion = libs.versions.checkstyle.get()
    configFile = projectDir.resolve("custom_checks.xml")
}

codenarc {
    toolVersion = libs.versions.codenarc.get()
    configFile = projectDir.resolve("custom_rules.xml")
}

dependencies {
    ktlintRuleset(project(":$releaseArtifact-ktlint"))

    checkstyle(project(":$releaseArtifact-checkstyle"))

    codenarc(project(":$releaseArtifact-codenarc"))

    implementation(libs.groovy.all)
}
