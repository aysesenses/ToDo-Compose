package com.ayse.todocompose.navigation.destination

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ayse.todocompose.util.Action
import com.ayse.todocompose.util.Constants.TASK_ARGUMENT_KEY
import com.ayse.todocompose.util.Constants.TASK_SCREEN

fun NavGraphBuilder.taskComposable(
    navigateToTaskScreen: (Action) -> Unit
) {
    composable(
        route = TASK_SCREEN,
        arguments = listOf(navArgument(TASK_ARGUMENT_KEY) {
            type = NavType.IntType
        })
    ) {

    }
}