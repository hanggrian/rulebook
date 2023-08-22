pluginManagement.repositories {
    gradlePluginPortal()
    mavenCentral()
}
dependencyResolutionManagement.repositories.mavenCentral()

rootProject.name = "rulebook"

include("rulebook-checkstyle", "rulebook-ktlint", "rulebook-codenarc")
include("sample-checkstyle", "sample-ktlint", "sample-codenarc")
include("website")
