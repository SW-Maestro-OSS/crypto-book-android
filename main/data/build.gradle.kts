plugins {
    alias(libs.plugins.cryptobook.android.library)
    alias(libs.plugins.cryptobook.spotless)
}

android {
    namespace = "io.soma.cryptobook.main.data"
}