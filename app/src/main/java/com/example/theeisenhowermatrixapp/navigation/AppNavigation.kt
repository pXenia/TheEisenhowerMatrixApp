package com.example.theeisenhowermatrixapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.theeisenhowermatrixapp.auth.presentation.LoginScreen
import com.example.theeisenhowermatrixapp.auth.presentation.RegisterScreen

@Composable
fun AppNavigation(
    isAuthorized: Boolean
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController, startDestination = if (isAuthorized) Screen.Matrix.route
        else Screen.Login.route
    ) {

        composable(Screen.Login.route) {
            LoginScreen(onLoginSuccess = {
                navController.navigate(Screen.Matrix.route) {
                    popUpTo(0)
                }
            }, onRegisterClick = {
                navController.navigate(Screen.Register.route)
            })
        }

        composable(Screen.Register.route) {
            RegisterScreen(onRegisterSuccess = {
                navController.navigate(Screen.Matrix.route) {
                    popUpTo(0)
                }
            }, onBackToLogin = {
                navController.popBackStack()
            })
        }

        composable(Screen.Matrix.route) {
            MainScreen()
        }
    }
}
