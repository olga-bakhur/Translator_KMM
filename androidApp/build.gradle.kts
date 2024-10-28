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
    namespace = libs.versions.android.applicationId.get().toString()
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = libs.versions.android.applicationId.get().toString()
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = libs.versions.android.versionCode.get().toInt()
        versionName = libs.versions.android.versionName.get().toString()

        testInstrumentationRunner = "com.bakhur.translator.TranslateAppTest"
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

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.compose.ui)
    debugImplementation(libs.compose.ui.tooling)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.foundation)
    implementation(libs.compose.runtime)
    implementation(libs.compose.material3)
    implementation(libs.compose.icons.extended)
    implementation(libs.androidx.activity.compose)
    implementation(libs.compose.navigation)
    implementation(libs.coil.compose)

    implementation(libs.androidx.lifecycle.viewmodel.android)

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