package com.ayse.todocompose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.ayse.todocompose.navigation.destination.listComposable
import com.ayse.todocompose.navigation.destination.taskComposable
import com.ayse.todocompose.util.Constants.LIST_SCREEN

@Composable
fun SetupNavigation(
    navHostController: NavHostController
){
    val screen = remember(navHostController){
        Screens(navHostController = navHostController)
    }

    NavHost(
        navController = navHostController,
    startDestination = LIST_SCREEN){
        listComposable(
            navigateToTaskScreen = screen.task
        )

        taskComposable(
            navigateToTaskScreen = screen.list
        )
    }
}