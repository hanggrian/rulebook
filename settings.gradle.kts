pluginManagement.repositories {
    gradlePluginPortal()
    mavenCentral()
}
dependencyResolutionManagement.repositories.mavenCentral()

rootProject.name = "convention-ktlint"
include("ktlint-rules")
include("sample")
include("website")
