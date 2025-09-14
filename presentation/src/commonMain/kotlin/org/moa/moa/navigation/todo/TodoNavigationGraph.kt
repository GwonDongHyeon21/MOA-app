package org.moa.moa.navigation.todo

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.moa.moa.presentation.todo.TodoScreen

fun NavGraphBuilder.todoNavigationGraph(navController: NavController) {
    composable(TodoNavigationItem.Todo.route) {
        TodoScreen()
    }
}