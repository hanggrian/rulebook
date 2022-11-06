plugins {
    java
    checkstyle
}

dependencies {
    checkstyle(project(":$RELEASE_ARTIFACT-checkstyle"))
}
