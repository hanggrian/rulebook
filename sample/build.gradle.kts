val releaseArtifact: String by project

plugins {
    kotlin("jvm") version libs.versions.kotlin
    alias(libs.plugins.ktlint)
    java
    checkstyle
    groovy
    codenarc
}

dependencies {
    ktlintRuleset(project(":$releaseArtifact-ktlint"))

    checkstyle(project(":$releaseArtifact-checkstyle"))

    codenarc(project(":$releaseArtifact-codenarc"))

    implementation(libs.groovy.all)
}

ktlint.kotlinScriptAdditionalPaths {
    include(fileTree("kts/"))
}

tasks {
    val codenarcScript by registering(CodeNarc::class) {
        setSource(layout.projectDirectory.file("gradle/"))
        include("*.gradle")
    }
    check { dependsOn(codenarcScript.get()) }
}
