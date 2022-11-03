pluginManagement.repositories {
    gradlePluginPortal()
    mavenCentral()
}
dependencyResolutionManagement.repositories.mavenCentral()

rootProject.name = "codestyle-ktlint"
include("ktlint")
include("sample")
include("website")
