package io.soma.cryptobook.main.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation3.runtime.NavKey
import dagger.hilt.android.AndroidEntryPoint
import io.soma.cryptobook.core.domain.navigation.NavigationHelper
import io.soma.cryptobook.home.presentation.navigation.HomeNavKey
import io.soma.cryptobook.main.presentation.navigation.LinkRouter
import io.soma.cryptobook.main.presentation.navigation.NavCommandSource
import io.soma.cryptobook.main.presentation.splash.SplashViewModel
import javax.inject.Inject

private const val TAG = "CBLOG_MainActivity"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var linkRouter: LinkRouter

    @Inject
    lateinit var navSource: NavCommandSource

    @Inject
    lateinit var navigationHelper: NavigationHelper

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        splashScreen.setKeepOnScreenCondition {
            !viewModel.isReady.value
        }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val initialScreen: NavKey =
            intent?.dataString?.let { linkRouter.resolve(it) } ?: HomeNavKey

        setContent {
            CryptoBookApp(
                navSource = navSource,
                linkRouter = linkRouter,
                initialScreen = initialScreen,
            )
        }
    }
}
