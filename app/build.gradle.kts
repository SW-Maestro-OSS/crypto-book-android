plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.cryptobook.hilt)
    alias(libs.plugins.cryptobook.spotless)
}

android {
    namespace = "io.soma.cryptobook"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "io.soma.cryptobook"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(projects.main.presentation)

    implementation(projects.home.data)
    implementation(projects.core.network)
    implementation(projects.core.domain)

    // Networking (for DI modules)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)

    implementation(projects.settings.domain)
    implementation(projects.settings.data)

    implementation(libs.androidx.dataStore)
    implementation(libs.androidx.core.ktx)
}
