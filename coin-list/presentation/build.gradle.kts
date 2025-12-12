plugins {
    alias(libs.plugins.cryptobook.android.library)
    alias(libs.plugins.cryptobook.hilt)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "io.soma.cryptobook.coinlist.presentation"

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(projects.core.presentation)
    implementation(projects.coinList.domain)

    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}