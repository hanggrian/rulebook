plugins {
    java
    checkstyle
}

checkstyle {
    toolVersion = libs.versions.checkstyle.get()
    configFile = projectDir.resolve("lints_checks.xml")
}

dependencies {
    checkstyle(libs.checkstyle)
    checkstyle(project(":$RELEASE_ARTIFACT-checkstyle"))
}
