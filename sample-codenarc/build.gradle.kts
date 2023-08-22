val RELEASE_ARTIFACT: String by project

plugins {
    groovy
    codenarc
}

codenarc {
    toolVersion = libs.versions.codenarc.get()
    configFile = rootDir.resolve("rulebook_codenarc.xml")
}

dependencies {
    codenarc(libs.codenarc)
    codenarc(project(":$RELEASE_ARTIFACT-codenarc"))
    implementation(libs.groovy.all)
}
