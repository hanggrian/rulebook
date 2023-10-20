val releaseArtifact: String by project

plugins {
    java
    checkstyle
}

checkstyle {
    toolVersion = libs.versions.checkstyle.get()
    configFile = rootDir.resolve("rulebook_checkstyle.xml")
}

dependencies {
    checkstyle(project(":$releaseArtifact-checkstyle"))
}
