package com.ayse.todocompose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.ayse.todocompose.navigation.destination.listComposable
import com.ayse.todocompose.navigation.destination.splashComposable
import com.ayse.todocompose.navigation.destination.taskComposable
import com.ayse.todocompose.ui.viewModel.SharedViewModel
import com.ayse.todocompose.util.Constants.LIST_SCREEN
import com.ayse.todocompose.util.Constants.SPLASH_SCREEN

@Composable
fun SetupNavigation(
    navHostController: NavHostController,
    sharedViewModel: SharedViewModel
){
    val screen = remember(navHostController){
        Screens(navHostController = navHostController)
    }

    NavHost(
        navController = navHostController,
        startDestination = SPLASH_SCREEN
    ) {
        splashComposable(
            navigateToListScreen = screen.splash
        )

        listComposable(
            navigateToTaskScreen = screen.list,
            sharedViewModel = sharedViewModel
        )

        taskComposable(
            navigateToListScreen = screen.task,
            sharedViewModel = sharedViewModel
        )
    }
}