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

        androidMain.dependencies {}

        commonMain.dependencies {
            implementation(libs.kotlinx.serialization.json)
        }

        desktopMain.dependencies {}

        nativeMain.dependencies {}

        all {
            languageSettings {
                optIn("kotlin.uuid.ExperimentalUuidApi")
            }
        }
    }
}

android {
    namespace = "at.tfro.sonic_link.interim"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
