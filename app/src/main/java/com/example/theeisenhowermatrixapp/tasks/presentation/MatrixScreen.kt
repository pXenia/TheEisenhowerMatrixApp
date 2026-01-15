package com.example.theeisenhowermatrixapp.tasks.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.theeisenhowermatrixapp.tasks.domain.Task
import com.example.theeisenhowermatrixapp.tasks.presentation.uicomponents.Quadrant
import com.example.theeisenhowermatrixapp.ui.theme.AccentBlue
import com.example.theeisenhowermatrixapp.ui.theme.GrayTextSecondary
import com.example.theeisenhowermatrixapp.ui.theme.QuadrantGreen
import com.example.theeisenhowermatrixapp.ui.theme.QuadrantGreenText
import com.example.theeisenhowermatrixapp.ui.theme.QuadrantNeutral
import com.example.theeisenhowermatrixapp.ui.theme.QuadrantRed
import com.example.theeisenhowermatrixapp.ui.theme.QuadrantRedText
import com.example.theeisenhowermatrixapp.ui.theme.QuadrantYellow
import com.example.theeisenhowermatrixapp.ui.theme.QuadrantYellowText

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EisenhowerMatrixScreen(
    viewModel: MatrixViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    var taskToEdit by remember { mutableStateOf<Task?>(null) }
    var showAddDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadAllTasks()
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Quadrant(
                    modifier = Modifier.weight(1f).fillMaxHeight(),
                    containerColor = QuadrantRed,
                    title = "Срочно и важно",
                    titleColor = QuadrantRedText,
                    tasks = uiState.q1Tasks,
                    onTaskCheckedChange = viewModel::changeStatus,
                    onTaskClick = { task -> taskToEdit = task }

                )

                Quadrant(
                    modifier = Modifier.weight(1f).fillMaxHeight(),
                    containerColor = QuadrantGreen,
                    title = "Важно, не срочно",
                    titleColor = QuadrantGreenText,
                    tasks = uiState.q2Tasks,
                    onTaskCheckedChange = viewModel::changeStatus,
                    onTaskClick = { task -> taskToEdit = task }
                )
            }

            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Quadrant(
                    modifier = Modifier.weight(1f).fillMaxHeight(),
                    containerColor = QuadrantYellow,
                    title = "Срочно, не важно",
                    titleColor = QuadrantYellowText,
                    tasks = uiState.q3Tasks,
                    onTaskCheckedChange = viewModel::changeStatus,
                    onTaskClick = { task -> taskToEdit = task }
                )

                Quadrant(
                    modifier = Modifier.weight(1f).fillMaxHeight(),
                    containerColor = QuadrantNeutral,
                    title = "Не срочно, не важно",
                    titleColor = GrayTextSecondary,
                    tasks = uiState.q4Tasks,
                    onTaskCheckedChange = viewModel::changeStatus,
                    onTaskClick = { task -> taskToEdit = task }
                )
            }
        }

        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = AccentBlue
            )
        }

        if (showAddDialog || taskToEdit != null) {
            AddTaskDialog(
                taskToEdit = taskToEdit,
                onDismiss = {
                    showAddDialog = false
                    taskToEdit = null
                },
                onAddTask = { task ->
                    viewModel.addTask(task)
                    showAddDialog = false
                },
                onUpdateTask = { id, request ->
                    viewModel.updateTask(id, request)
                    taskToEdit = null
                }
            )
        }

        uiState.error?.let { error ->
            Card(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.Black)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Ошибка загрузки",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = error,
                            color = Color.White,
                            fontSize = 12.sp
                        )
                    }
                    TextButton(onClick = { viewModel.retry() }) {
                        Text("Повторить", color = Color.White)
                    }
                }
            }
        }
    }
}