package com.ayse.todocompose.ui.screens.list

import android.annotation.SuppressLint
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ayse.todocompose.R
import com.ayse.todocompose.ui.theme.fabBackgroundColor
import com.ayse.todocompose.ui.viewModel.SharedViewModel
import com.ayse.todocompose.util.Action
import com.ayse.todocompose.util.SearchAppBarState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ListScreen(
    navigateToTaskScreen: (taskID: Int) -> Unit,
    sharedViewModel: SharedViewModel
) {

    LaunchedEffect(key1 = true){
        sharedViewModel.getAllTasks()
        sharedViewModel.readSortState()
    }

    val action by sharedViewModel.action

    val allTasks by sharedViewModel.allTask.collectAsState()
    val searchedTasks by sharedViewModel.searchedTasks.collectAsState()

    val sortState by sharedViewModel.sortState.collectAsState()
    val lowPriorityTasks by sharedViewModel.lowPriorityTasks.collectAsState()
    val highPriorityTasks by sharedViewModel.highPriorityTasks.collectAsState()

    val searchAppBarState: SearchAppBarState by sharedViewModel.searchAppBarState
    val searchTextState: String by sharedViewModel.searchTextState

    val scaffoldState = rememberScaffoldState()

    DisplaySnackbar(
        scaffoldState = scaffoldState,
        handleDatabaseActions = { sharedViewModel.handleDatabaseActions(action = action) },
        onUndoClicked = {
               sharedViewModel.action.value = it
        },
        taskTitle = sharedViewModel.title.value,
        action = action
    )

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            ListAppBar(
                sharedViewModel = sharedViewModel,
                searchAppBarState = searchAppBarState,
                searchTextState = searchTextState
            )
        },
        content = {
            ListContent(
                allTasks = allTasks,
                searchedTasks = searchedTasks,
                searchAppBarState = searchAppBarState,
                lowPriorityTasks = lowPriorityTasks,
                highPriorityTasks = highPriorityTasks,
                sortState = sortState,
                navigateToTaskScreen = navigateToTaskScreen,
                onSwipeToDelete = {
                    action, task ->
                    sharedViewModel.action.value = action
                    sharedViewModel.updateTaskFields(selectedTask = task)

                }
            )
        },
        floatingActionButton = {
            ListFab(onFabClicked = navigateToTaskScreen)
        }
    )
}

@Composable
fun ListFab(
    onFabClicked: (taskID: Int) -> Unit
) {
    FloatingActionButton(
        onClick = {
            onFabClicked(-1)
        },
        backgroundColor = MaterialTheme.colors.fabBackgroundColor
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(id = R.string.add_button),
            tint = Color.White
        )
    }
}

@Composable
fun DisplaySnackbar(
    scaffoldState: ScaffoldState,
    handleDatabaseActions: () -> Unit,
    onUndoClicked: (Action) -> Unit,
    taskTitle: String,
    action: Action
){
    handleDatabaseActions()

    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = action) {
        if (action != Action.NO_ACTION) {
            scope.launch {
                val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                    message = setMessage(action = action, taskTitle = taskTitle),
                    actionLabel = setActionLabel(action = action)
                )
                undoDeletedTask(
                    action = action,
                    snackbarResult = snackbarResult,
                    undoClicked = onUndoClicked
                )
            }
        }
    }
}

private fun setMessage(action: Action, taskTitle: String): String {
    return when (action) {
        Action.DELETE_ALL -> "All Tasks Removed."
        else -> "${action.name}: $taskTitle"
    }

}

private fun setActionLabel(action: Action): String {
    return if (action.name == "DELETE") {
        "UNDO"
    } else {
        "OK"
    }
}

private fun undoDeletedTask (
    action: Action,
    snackbarResult: SnackbarResult,
    undoClicked : (Action) -> Unit
){
    if (snackbarResult == SnackbarResult.ActionPerformed && action == Action.DELETE) {
        undoClicked(Action.UNDO)
    }
}