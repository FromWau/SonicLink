import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
     androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    jvm("desktop")

    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            implementation(libs.koin.android)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.koin.logger.slf4j)
        }

        commonMain.dependencies {
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)

            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.datetime)

            implementation(libs.koin.core)
            implementation(libs.koin.ktor)

            implementation(libs.bundles.ktor)
        }

        desktopMain.dependencies {
            implementation(libs.ktor.client.okhttp)
            implementation(libs.koin.logger.slf4j)
        }

        nativeMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }

        all {
            languageSettings {
                optIn("kotlin.uuid.ExperimentalUuidApi")
            }
        }
    }
}

android {
    namespace = "at.tfro.sonic_link.core"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
