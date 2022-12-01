pluginManagement.repositories {
    gradlePluginPortal()
    mavenCentral()
}
dependencyResolutionManagement.repositories.mavenCentral()

rootProject.name = "rulebook"

include("rulebook-checkstyle", "rulebook-ktlint")
include("sample-checkstyle", "sample-ktlint")
include("website")
