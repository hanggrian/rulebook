val releaseArtifact: String by project

plugins {
    kotlin("jvm") version libs.versions.kotlin
    java
    checkstyle
    groovy
    codenarc
}

java.sourceSets.main {
    java.srcDir("tests/java")
}

kotlin.sourceSets.main {
    kotlin.srcDir("tests/kotlin")
}

ktlint.kotlinScriptAdditionalPaths {
    include(fileTree("kts/"))
}

dependencies {
    checkstyle(project(":$releaseArtifact-checkstyle"))

    codenarc(project(":$releaseArtifact-codenarc"))

    implementation(libs.groovy.all)

    compileOnly(kotlin("test-junit", libs.versions.kotlin.get()))
}

tasks {
    val codenarcScript by registering(CodeNarc::class) {
        setSource(layout.projectDirectory.file("gradle/"))
        include("*.gradle")
    }
    check {
        dependsOn(codenarcScript.get())
    }
}
