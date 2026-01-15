package com.example.theeisenhowermatrixapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.theeisenhowermatrixapp.auth.data.TokenManager
import com.example.theeisenhowermatrixapp.navigation.AppNavigation
import com.example.theeisenhowermatrixapp.ui.theme.TheEisenhowerMatrixAppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val token by tokenManager.getToken()
                .collectAsState(initial = null)

            TheEisenhowerMatrixAppTheme {
                AppNavigation(
                    isAuthorized = token != null
                )
            }
        }
    }
}

