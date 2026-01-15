package com.example.theeisenhowermatrixapp.stats.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.theeisenhowermatrixapp.auth.presentation.ChangePasswordDialog
import com.example.theeisenhowermatrixapp.ui.theme.GrayTextSecondary
import com.example.theeisenhowermatrixapp.ui.theme.QuadrantGreenText
import com.example.theeisenhowermatrixapp.ui.theme.QuadrantRedText
import com.example.theeisenhowermatrixapp.ui.theme.QuadrantYellowText

@Composable
fun StatsScreen(
    viewModel: StatsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showChangePassword by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadStats()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        UserHeader(
            username = uiState.username,
            email = uiState.email,
            onChangePasswordClick = { showChangePassword = true },
            logout = { viewModel.logout() })

        Spacer(Modifier.height(16.dp))

        Text(
            text = "Статистика задач",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        if (uiState.isLoading) {
            CircularProgressIndicator()
            return
        }

        uiState.error?.let {
            Text(it, color = MaterialTheme.colorScheme.error)
        }

        uiState.taskStats?.let { stats ->
            StatsCard("Всего задач", stats.totalTasks.toString())
            Text("По квадрантам", fontWeight = FontWeight.Bold)
            QuadrantStat("Q1", stats.byQuadrant["Q1"] ?: 0, QuadrantRedText)
            QuadrantStat("Q2", stats.byQuadrant["Q2"] ?: 0, QuadrantGreenText)
            QuadrantStat("Q3", stats.byQuadrant["Q3"] ?: 0, QuadrantYellowText)
            QuadrantStat("Q4", stats.byQuadrant["Q4"] ?: 0, GrayTextSecondary)
        }

        uiState.timingStats?.let { timing ->
            Text("Сроки выполнения", fontWeight = FontWeight.Bold)
            StatsRow("Вовремя", timing.completedOnTime)
            StatsRow("С опозданием", timing.completedLate)
        }
    }

    if (showChangePassword) {
        ChangePasswordDialog(
            isLoading = uiState.isChangingPassword,
            onDismiss = { showChangePassword = false },
            onConfirm = { old, new ->
                viewModel.changePassword(old, new)
            })
    }

    if (uiState.passwordChanged) {
        LaunchedEffect(Unit) {
            showChangePassword = false
            viewModel.resetPasswordChanged()
        }
    }
}


@Composable
fun StatsCard(title: String, value: String) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = Color.Transparent,
        border = BorderStroke(1.dp, Color.Black)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(title, style = MaterialTheme.typography.bodyMedium)
            Text(value, style = MaterialTheme.typography.headlineMedium)
        }
    }
}

@Composable
fun StatsRow(label: String, value: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label)
        Text(value.toString(), fontWeight = FontWeight.Bold)
    }
}

@Composable
fun QuadrantStat(title: String, value: Int, color: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(title, color = color)
        Text(value.toString(), fontWeight = FontWeight.Bold)
    }
}
