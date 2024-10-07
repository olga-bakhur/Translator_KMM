plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.sqlDelightPlugin)
}

kotlin {
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
            implementation(libs.ktor.core)
            implementation(libs.ktor.serialization)
            implementation(libs.ktor.serializationJson)
            implementation(libs.sqlDelight.runtime)
            implementation(libs.sqlDelight.coroutinesExtensions)
            implementation(libs.dateTime)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.assertK)
            implementation(libs.turbine)
        }
        androidMain.dependencies {
            implementation(libs.ktor.android)
            implementation(libs.sqlDelight.androidDriver)
        }
        iosMain.dependencies {
//            implementation(libs.ktor.iOS)
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

//sqldelight {
//    database("NoteDatabase") {
//        packageName = "com.bakhur.translator.database"
//        sourceFolders = listOf("sqldelight")
//    }
//}