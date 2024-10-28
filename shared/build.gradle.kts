plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.sqlDelight)
    alias(libs.plugins.buildConfig)
    alias(libs.plugins.ksp)
    alias(libs.plugins.ktorfit)
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
        version = libs.versions.ios.appVersion.get().toString()
        ios.deploymentTarget = libs.versions.ios.deploymentTarget.get().toString()
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
            implementation(libs.ktorfit)
            implementation(libs.ktorfit.converters.response)
            implementation(libs.ktorfit.converters.call)
            implementation(libs.ktorfit.converters.flow)
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
            implementation(libs.ktor.client.android)
            implementation(libs.slf4j.android)
            implementation(libs.sqlDelight.androidDriver)

            implementation(libs.ktor.client.okhttp)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.ios)
            implementation(libs.sqlDelight.nativeDriver)
        }
    }
}

android {
    namespace = libs.versions.shared.namespace.get()
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
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

buildConfig {
    buildConfigField("String", "BASE_URL", "\"https://translate.pl-coding.com/\"")
}