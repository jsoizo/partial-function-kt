import com.vanniktech.maven.publish.SonatypeHost

plugins {
    val kotlinVersion = "1.9.20"
    kotlin("multiplatform") version kotlinVersion
    id("org.jetbrains.kotlinx.kover") version "0.7.5"
    id("org.jetbrains.dokka") version "1.9.10"
    id("com.android.library") version "8.2.0"
    id("com.vanniktech.maven.publish") version "0.28.0"
}

group = "com.jsoizo"
version = "0.1.0"

repositories {
    mavenCentral()
    google()
}

kotlin {
    applyDefaultHierarchyTemplate()

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

    jvm {
        withSourcesJar(true)
    }
    androidTarget {
        publishLibraryVariants("release", "debug")
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    iosArm64()
    iosSimulatorArm64()
    linuxX64()
    macosArm64()
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
    compileSdk = 34
    defaultConfig {
        minSdk = 28
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

mavenPublishing {
    coordinates(
        groupId = group.toString(),
        artifactId = "partial-function-kt",
        version = version.toString()
    )

    pom {
        name.set("partial-function-kt")
        description.set("Partial Function in Kotlin")
        url.set("https://github.com/jsoizo/partial-function-kt")
        licenses {
            license {
                name.set("MIT License")
                url.set("https://github.com/jsoizo/partial-function-kt/blob/master/LICENSE")
                distribution.set("repo")

            }
        }
        developers {
            developer {
                id.set("jsoizo")
                name.set("Jun Sekine")
                email.set("jun@sakamo.to")
            }
        }
        scm {
            url.set("https://github.com/jsoizo/partial-function-kt")
        }
    }

    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()
}