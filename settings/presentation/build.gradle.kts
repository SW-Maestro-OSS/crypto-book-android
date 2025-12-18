plugins {
    alias(libs.plugins.cryptobook.android.library)
    alias(libs.plugins.cryptobook.hilt)
    alias(libs.plugins.cryptobook.android.library.compose)
}

android {
    namespace = "io.soma.cryptobook.settings.presentation"
}

dependencies {
    implementation(projects.core.presentation)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}