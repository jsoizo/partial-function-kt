plugins {
    val kotlinVersion = "1.9.20"
    kotlin("multiplatform") version kotlinVersion
    id("org.jetbrains.kotlinx.kover") version "0.7.5"
    id("org.jetbrains.dokka") version "1.9.10"
    id("com.android.library") version "8.2.0"
    `maven-publish`
}

group = "com.jsoizo"
version = "0.1.0"

repositories {
    mavenCentral()
    google()
}

kotlin {
    applyDefaultHierarchyTemplate()
    jvm {
        withSourcesJar()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                // common dependencies
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }

    androidTarget {
        publishLibraryVariants("release")
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    iosArm64()
    iosSimulatorArm64()
    linuxX64()
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}

android {
    namespace = "com.jsoizo.paritialfunction"
    compileSdk = 35
}