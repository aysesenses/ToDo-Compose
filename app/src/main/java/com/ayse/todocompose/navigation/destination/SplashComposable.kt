package com.ayse.todocompose.navigation.destination

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ayse.todocompose.ui.screens.splash.SplashScreen
import com.ayse.todocompose.util.Constants

fun NavGraphBuilder.splashComposable(
    navigateToListScreen: () -> Unit
) {
    composable(
        route = Constants.SPLASH_SCREEN
    ) {
        SplashScreen(
            navigateToListScreen = navigateToListScreen
        )
    }
}