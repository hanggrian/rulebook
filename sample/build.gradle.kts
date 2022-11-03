plugins {
    application
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.kapt)
}

application.mainClass.set("com.example.MyApp")

dependencies {
    ktlint(libs.ktlint, ::ktlintAttributes)
    ktlint(project(":ktlint"))
}
