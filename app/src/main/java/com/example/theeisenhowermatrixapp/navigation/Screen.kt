package com.example.theeisenhowermatrixapp.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")

    object Matrix : Screen("matrix")
    object TasksList : Screen("tasks")
    object Profile : Screen("profile")
    object Stats: Screen("stats")
}
