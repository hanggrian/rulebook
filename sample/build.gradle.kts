val releaseArtifact: String by project

plugins {
    kotlin("jvm") version libs.versions.kotlin
    alias(libs.plugins.ktlint)
    java
    checkstyle
    groovy
    codenarc
}

codenarc {
    toolVersion = libs.versions.codenarc.get()
    configFile = rootDir.resolve("rulebook_codenarc.xml")
}

checkstyle {
    toolVersion = libs.versions.checkstyle.get()
    configFile = rootDir.resolve("rulebook_checkstyle.xml")
}

dependencies {
    ktlintRuleset(project(":$releaseArtifact-ktlint"))
    checkstyle(project(":$releaseArtifact-checkstyle"))
    codenarc(project(":$releaseArtifact-codenarc"))

    implementation(libs.groovy.all)
}
