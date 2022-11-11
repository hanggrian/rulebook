pluginManagement.repositories {
    gradlePluginPortal()
    mavenCentral()
}
dependencyResolutionManagement.repositories.mavenCentral()

rootProject.name = "lints"
include("lints-checkstyle")
include("lints-ktlint")
include("sample-checkstyle")
include("sample-ktlint")
include("website")
