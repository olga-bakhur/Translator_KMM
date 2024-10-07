plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
//    alias(libs.plugins.sqlDelightPlugin)
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
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.toString()
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
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)
    implementation(libs.androidx.activity.compose)
    debugImplementation(libs.compose.ui.tooling)

    implementation(libs.compose.foundation)
    implementation(libs.compose.icons.extended)
    implementation(libs.compose.navigation)
    implementation(libs.coil)

    implementation(libs.hilt.android)
    ksp(libs.hilt.androidCompiler)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    implementation(libs.ktor.android)

    androidTestImplementation(libs.testRunner)
    androidTestImplementation(libs.jUnit)
    androidTestImplementation(libs.compose.testing)
    debugImplementation(libs.compose.test.manifest)

    kspAndroidTest(libs.hilt.androidCompiler)
    androidTestImplementation(libs.hilt.testing)
}