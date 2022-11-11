plugins {
    java
    checkstyle
}

dependencies {
    checkstyle(libs.checkstyle)
    checkstyle(project(":$RELEASE_ARTIFACT-checkstyle"))
}
