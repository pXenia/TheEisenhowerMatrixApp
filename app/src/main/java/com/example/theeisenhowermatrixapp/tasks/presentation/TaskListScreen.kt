package com.example.theeisenhowermatrixapp.tasks.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.theeisenhowermatrixapp.tasks.presentation.uicomponents.EmptyTasksPlaceholder
import com.example.theeisenhowermatrixapp.tasks.presentation.uicomponents.ErrorCard
import com.example.theeisenhowermatrixapp.tasks.presentation.uicomponents.TaskFilterRow
import com.example.theeisenhowermatrixapp.tasks.presentation.uicomponents.TaskItem
import com.example.theeisenhowermatrixapp.tasks.presentation.util.TaskFilter
import com.example.theeisenhowermatrixapp.ui.theme.AccentBlue
import com.example.theeisenhowermatrixapp.ui.theme.BlackText
import com.example.theeisenhowermatrixapp.ui.theme.WhiteBackground

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskListScreen(
    viewModel: MatrixViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val allTasks by viewModel.allTasks.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    var selectedFilter by remember { mutableStateOf(TaskFilter.ALL) }
    var showAddDialog by remember { mutableStateOf(false) }

    val filteredTasks = remember(allTasks, selectedFilter) {
        when (selectedFilter) {
            TaskFilter.ALL -> allTasks
            TaskFilter.Q1 -> allTasks.filter { it.quadrant == "Q1" }
            TaskFilter.Q2 -> allTasks.filter { it.quadrant == "Q2" }
            TaskFilter.Q3 -> allTasks.filter { it.quadrant == "Q3" }
            TaskFilter.Q4 -> allTasks.filter { it.quadrant == "Q4" }
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(WhiteBackground)
        ) {

            Text(
                text = "Все задачи",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = BlackText,
                modifier = Modifier.padding(16.dp)
            )

            TaskFilterRow(
                selectedFilter = selectedFilter,
                onFilterSelected = { selectedFilter = it }
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (filteredTasks.isEmpty() && !uiState.isLoading) {
                EmptyTasksPlaceholder()
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filteredTasks, key = { it.id }) { task ->
                        TaskItem(
                            task = task,
                            onDeleteClick = { viewModel.deleteTask(task.id) },
                            onCheckedChange = {viewModel.changeStatus(task.id)}
                        )
                    }
                }
            }
        }

        // FAB
        FloatingActionButton(
            onClick = { showAddDialog = true },
            containerColor = AccentBlue,
            contentColor = Color.White,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Добавить задачу")
        }

        if (showAddDialog) {
            AddTaskDialog(
                onDismiss = { showAddDialog = false },
                onAddTask = { task ->
                    viewModel.addTask(task)
                    showAddDialog = false
                }
            )
        }

        uiState.error?.let { error ->
            ErrorCard(
                error = error,
                onRetry = { viewModel.retry() },
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}
