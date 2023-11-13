pluginManagement.repositories {
    gradlePluginPortal()
    mavenCentral()
}
dependencyResolutionManagement.repositories.mavenCentral()

rootProject.name = "rulebook"

include("rulebook-checkstyle", "rulebook-ktlint", "rulebook-codenarc")
include("sample", "sample-library")
include("website")
