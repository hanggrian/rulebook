import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinJvm
import com.vanniktech.maven.publish.MavenPublishBaseExtension
import com.vanniktech.maven.publish.MavenPublishBasePlugin
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jlleitschuh.gradle.ktlint.KtlintExtension
import org.jlleitschuh.gradle.ktlint.KtlintPlugin

val developerId: String by project
val developerName: String by project
val developerUrl: String by project
val releaseGroup: String by project
val releaseArtifact: String by project
val releaseVersion: String by project
val releaseDescription: String by project
val releaseUrl: String by project

val javaCompileVersion: JavaLanguageVersion =
    JavaLanguageVersion.of(libs.versions.java.compile.get())
val javaSupportVersion: JavaLanguageVersion =
    JavaLanguageVersion.of(libs.versions.java.support.get())

plugins {
    kotlin("jvm") version libs.versions.kotlin apply false
    alias(libs.plugins.dokka)
    alias(libs.plugins.dokka.javadoc) apply false
    alias(libs.plugins.ktlint) apply false
    alias(libs.plugins.maven.publish) apply false
}

allprojects {
    group = releaseGroup
    version = releaseVersion
}

subprojects {
    plugins.withType<KotlinPluginWrapper>().configureEach {
        the<KotlinJvmProjectExtension>().jvmToolchain(javaCompileVersion.asInt())
    }
    plugins.withType<KtlintPlugin>().configureEach {
        the<KtlintExtension>().version.set(libs.versions.ktlint.get())
    }
    plugins.withType<MavenPublishBasePlugin> {
        configure<MavenPublishBaseExtension> {
            configure(KotlinJvm(JavadocJar.Dokka("dokkaGeneratePublicationJavadoc")))
            publishToMavenCentral()
            signAllPublications()
            pom {
                name.set(project.name)
                description.set(releaseDescription)
                url.set(releaseUrl)
                inceptionYear.set("2024")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                        distribution.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set(developerId)
                        name.set(developerName)
                        url.set(developerUrl)
                    }
                }
                scm {
                    url.set(releaseUrl)
                    connection.set("scm:git:https://github.com/$developerId/$releaseArtifact.git")
                    developerConnection
                        .set("scm:git:ssh://git@github.com/$developerId/$releaseArtifact.git")
                }
            }
        }
    }

    tasks {
        withType<JavaCompile>().configureEach {
            options.release = javaSupportVersion.asInt()
        }
        withType<GroovyCompile>().configureEach {
            options.release = javaSupportVersion.asInt()
        }
        withType<KotlinCompile>().configureEach {
            compilerOptions.jvmTarget
                .set(JvmTarget.fromTarget(JavaVersion.toVersion(javaSupportVersion).toString()))
        }
        withType<Test>().configureEach {
            useJUnitPlatform()
        }
    }

    if (project.name.startsWith("sample")) {
        plugins.withType<JavaPlugin>().configureEach {
            the<JavaPluginExtension>().sourceSets.named("main").get().java.srcDir("java")
        }
        plugins.withType<GroovyPlugin>().configureEach {
            the<JavaPluginExtension>()
                .sourceSets
                .named("main")
                .get()
                .extensions
                .getByType<GroovySourceDirectorySet>()
                .srcDir("groovy")
        }
        plugins.withType<KotlinPluginWrapper>().configureEach {
            the<KotlinJvmProjectExtension>().sourceSets.named("main").get().kotlin.srcDir("kotlin")
        }
    }
}

dependencies {
    dokka(project(":$releaseArtifact-checkstyle"))
    dokka(project(":$releaseArtifact-codenarc"))
    dokka(project(":$releaseArtifact-ktlint"))
}
