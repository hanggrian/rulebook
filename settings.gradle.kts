pluginManagement.repositories {
    gradlePluginPortal()
    mavenCentral()
}
dependencyResolutionManagement.repositories.mavenCentral()

rootProject.name = "rulebook"

include(
    "rulebook-checkstyle",
    "rulebook-ktlint",
    "rulebook-codenarc",
    "codechecks:jvm",
)
include(
    "testings:checkstyle",
    "stress-tests:jvm",
)
include(
    "sample",
    "sample-configured",
)
