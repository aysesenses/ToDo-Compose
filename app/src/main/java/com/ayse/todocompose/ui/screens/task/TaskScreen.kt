package com.ayse.todocompose.ui.screens.task

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.ayse.todocompose.data.ToDoTask
import com.ayse.todocompose.data.models.Priority
import com.ayse.todocompose.ui.viewModel.SharedViewModel
import com.ayse.todocompose.util.Action

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TaskScreen(
    selectedTask : ToDoTask?,
    navigateToListScreen: (Action) -> Unit,
    sharedViewModel: SharedViewModel
){
    val title : String by sharedViewModel.title
    val description : String by sharedViewModel.description
    val priority : Priority by sharedViewModel.priority

   val context = LocalContext.current

    Scaffold (
        topBar = {
            TaskAppBar(
            navigateToListScreen = { action ->
                if (action == Action.NO_ACTION){
                    navigateToListScreen(action)
                }else {
                    if (sharedViewModel.validateFields()){
                        navigateToListScreen(action)
                    }else{
                        displayToast(context = context )
                    }
                }

            } ,
            selectedTask = selectedTask
        )
                 },
        content = {
            TaskContent(
                title = title,
                onTitleChange = {
                    sharedViewModel.updateTitle(it)
                },
                description = description,
                onDescriptionChange = {
                    sharedViewModel.description.value = it
                },
                priority = priority,
                onPrioritySelected = {
                    sharedViewModel.priority.value = it
                }
            )
        }
    )
}

fun displayToast(context: Context) {
    Toast.makeText(context, "Fields Empty.", Toast.LENGTH_SHORT).show()
}
