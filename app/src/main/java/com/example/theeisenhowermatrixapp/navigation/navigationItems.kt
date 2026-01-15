package com.example.theeisenhowermatrixapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Person

val navigationItems = listOf(
    NavigationItem(
        title = "Матрица",
        icon = Icons.Outlined.CheckCircle,
        route = Screen.Matrix.route
    ),
    NavigationItem(
        title = "Задачи",
        icon = Icons.Outlined.List,
        route = Screen.TasksList.route
    ),
    NavigationItem(
        title = "Профиль",
        icon = Icons.Outlined.Person,
        route = Screen.Profile.route
    )
)

