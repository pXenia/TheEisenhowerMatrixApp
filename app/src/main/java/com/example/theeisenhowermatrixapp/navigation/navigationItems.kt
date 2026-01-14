package com.example.theeisenhowermatrixapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star

val navigationItems = listOf(
    NavigationItem(
        title = "Матрица",
        icon = Icons.Default.Home,
        route = Screen.Matrix.route
    ),
    NavigationItem(
        title = "Задачи",
        icon = Icons.Default.Person,
        route = Screen.TasksList.route
    ),
    NavigationItem(
        title = "Профиль",
        icon = Icons.Default.ShoppingCart,
        route = Screen.Profile.route
    ),
    NavigationItem(
        title = "Статистика",
        icon = Icons.Default.Star,
        route = Screen.Stats.route
    ),
)

