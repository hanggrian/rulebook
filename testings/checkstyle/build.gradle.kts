plugins {
    kotlin("jvm") version libs.versions.kotlin.get()
}

dependencies {
    implementation(libs.checkstyle)
    implementation(libs.truth)
}
