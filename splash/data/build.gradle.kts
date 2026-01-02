plugins {
    alias(libs.plugins.cryptobook.android.library)
    alias(libs.plugins.cryptobook.hilt)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.cryptobook.spotless)
}

android {
    namespace = "io.soma.cryptobook.splash.data"
}

dependencies {
    implementation(projects.splash.domain)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
}