package com.ayse.todocompose.navigation

import androidx.navigation.NavHostController
import com.ayse.todocompose.util.Action
import com.ayse.todocompose.util.Constants.LIST_SCREEN
import com.ayse.todocompose.util.Constants.SPLASH_SCREEN

class Screens(navHostController: NavHostController) {

    val splash: () -> Unit = {
        navHostController.navigate(route = "list/${Action.NO_ACTION}") {
            popUpTo(SPLASH_SCREEN) { inclusive = true }
        }
    }
    val list: (Int) -> Unit = { taskId ->
        navHostController.navigate(route = "task/$taskId")
    }

    val task: (Action) -> Unit = { action ->
        navHostController.navigate(route = "list/${action}") {
            popUpTo(LIST_SCREEN) { inclusive = true }
        }
    }
}