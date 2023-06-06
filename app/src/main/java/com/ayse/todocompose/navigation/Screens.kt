package com.ayse.todocompose.navigation

import androidx.navigation.NavHostController
import com.ayse.todocompose.util.Action
import com.ayse.todocompose.util.Constants.LIST_SCREEN

class Screens(navHostController: NavHostController) {

    val list: (Action) -> Unit = { action ->
        navHostController.navigate("list/${action.name}") {
            popUpTo(LIST_SCREEN) { inclusive = true }
        }
    }

    val task: (Int) -> Unit = { taskId ->
        navHostController.navigate(route = "task/$taskId")
    }
}