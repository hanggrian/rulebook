pluginManagement.repositories {
    gradlePluginPortal()
    mavenCentral()
}
dependencyResolutionManagement.repositories.mavenCentral()

rootProject.name = "codestyle"
include("codestyle-checkstyle")
include("codestyle-ktlint")
include("sample-checkstyle")
include("sample-ktlint")
include("website")
