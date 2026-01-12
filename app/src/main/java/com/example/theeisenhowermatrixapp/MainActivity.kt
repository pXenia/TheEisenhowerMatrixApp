package com.example.theeisenhowermatrixapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.theeisenhowermatrixapp.navigation.AppNavigation
import com.example.theeisenhowermatrixapp.navigation.MainScreen
import com.example.theeisenhowermatrixapp.tasks.presentation.EisenhowerMatrixScreen
import com.example.theeisenhowermatrixapp.ui.theme.TheEisenhowerMatrixAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TheEisenhowerMatrixAppTheme {
                MainScreen()
            }
        }
    }
}
