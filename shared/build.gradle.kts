plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.sqlDelight)
}

kotlin {
    tasks.create("testClasses")

    androidTarget()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "16.0"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.bundles.ktor)
            implementation(libs.ktor.logging)
            implementation(libs.slf4j.api)
            implementation(libs.slf4j.simple)
            implementation(libs.sqlDelight.runtime)
            implementation(libs.sqlDelight.coroutines.extensions)
            implementation(libs.dateTime)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.assertK)
            implementation(libs.turbine)
        }
        androidMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.ktor.client.android)
            implementation(libs.slf4j.android)
            implementation(libs.sqlDelight.androidDriver)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.ios)
            implementation(libs.sqlDelight.nativeDriver)
        }
    }
}

android {
    namespace = "com.bakhur.translator"
    compileSdk = 35
    defaultConfig {
        minSdk = 28
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

sqldelight {
    databases {
        create("TranslateDatabase") {
            packageName.set("com.bakhur.translator.database")
        }
    }
}