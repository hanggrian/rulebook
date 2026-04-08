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
    "codecheck",
)
include(
    "testing",
    "stress-test",
)
include(
    "sample",
    "sample-configured",
)
