package com.example.theeisenhowermatrixapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.theeisenhowermatrixapp.tasks.presentation.EisenhowerMatrixScreen
import com.example.theeisenhowermatrixapp.tasks.presentation.TaskListScreen

@Composable
fun NavigationHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Matrix.route,
        modifier = modifier
    ) {
        composable(Screen.Matrix.route) {
            EisenhowerMatrixScreen()
        }
        composable(Screen.TasksList.route) {
            TaskListScreen()
        }
        composable(Screen.Profile.route) {
            ScreeTemp()
        }
    }
}

