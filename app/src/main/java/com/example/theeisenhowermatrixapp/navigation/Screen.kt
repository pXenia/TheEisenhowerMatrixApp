package com.example.theeisenhowermatrixapp.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login_screen")
    object Matrix: Screen("matrix_screen")
    object TasksList: Screen("tasks_list_screen")
    object Profile: Screen("profile_screen")
}