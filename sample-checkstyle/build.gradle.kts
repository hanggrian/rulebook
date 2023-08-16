plugins {
    java
    checkstyle
}

checkstyle {
    toolVersion = libs.versions.checkstyle.get()
    configFile = rootDir.resolve("rulebook_checks.xml")
}

dependencies {
    checkstyle(libs.checkstyle)
    checkstyle(project(":$RELEASE_ARTIFACT-checkstyle"))
}
