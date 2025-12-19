plugins {
    alias(libs.plugins.cryptobook.android.library.compose)
    alias(libs.plugins.cryptobook.android.presentation)
}

android {
    namespace = "io.soma.cryptobook.core.presentation"
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}