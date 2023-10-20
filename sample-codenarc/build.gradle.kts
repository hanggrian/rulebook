val releaseArtifact: String by project

plugins {
    groovy
    codenarc
}

codenarc {
    toolVersion = libs.versions.codenarc.get()
    configFile = rootDir.resolve("rulebook_codenarc.xml")
}

dependencies {
    codenarc(project(":$releaseArtifact-codenarc"))

    implementation(libs.groovy.all)
}
