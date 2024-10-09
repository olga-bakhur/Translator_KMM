plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.bakhur.translator.android"
    compileSdk = 35
    defaultConfig {
        applicationId = "com.bakhur.translator.android"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.bakhur.translator.TestHiltRunner"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.majorVersion
    }
}

dependencies {
    implementation(projects.shared)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.compose.ui)
    debugImplementation(libs.compose.ui.tooling)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.foundation)
    implementation(libs.compose.material3)
    implementation(libs.compose.icons.extended)
    implementation(libs.androidx.activity.compose)
    implementation(libs.compose.navigation)
    implementation(libs.coil.compose)

    implementation(libs.hilt.android)
    ksp(libs.hilt.androidCompiler)
    implementation(libs.hilt.navigation.compose)

    implementation(libs.ktor.client.android)

    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.testRunner)
    androidTestImplementation(libs.test.rule)
    androidTestImplementation(libs.jUnit)
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.compose.test.manifest)
    androidTestImplementation(libs.hilt.android.testing)

    kspAndroidTest(libs.hilt.androidCompiler)
}