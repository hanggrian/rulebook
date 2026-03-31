val releaseArtifact: String by project

plugins {
    kotlin("jvm") version libs.versions.kotlin
    java
    checkstyle
    groovy
    codenarc
}

java.sourceSets.main {
    java.srcDir("java-test")
}

kotlin.sourceSets.main {
    kotlin.srcDir("kotlin-test")
}

ktlint.kotlinScriptAdditionalPaths {
    include(fileTree("kotlin-script/"))
}

dependencies {
    checkstyle(project(":$releaseArtifact-checkstyle"))

    codenarc(project(":$releaseArtifact-codenarc"))

    implementation(libs.groovy.all)

    compileOnly(kotlin("test-junit", libs.versions.kotlin.get()))
}

tasks {
    val codenarcScript by registering(CodeNarc::class) {
        setSource(layout.projectDirectory.file("groovy-script/"))
        include("*.gradle")
    }
    check {
        dependsOn(codenarcScript.get())
    }
}
