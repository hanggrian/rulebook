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
    "codecheck-jvm",
)
include(
    "testing-checkstyle",
    "stress-test-jvm",
)
include(
    "sample",
    "sample-configured",
)
