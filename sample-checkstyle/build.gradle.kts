val RELEASE_ARTIFACT: String by project

plugins {
    java
    checkstyle
}

checkstyle {
    toolVersion = libs.versions.checkstyle.get()
    configFile = rootDir.resolve("rulebook_checkstyle.xml")
}

dependencies {
    checkstyle(libs.checkstyle)
    checkstyle(project(":$RELEASE_ARTIFACT-checkstyle"))
}
