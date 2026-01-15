package com.example.theeisenhowermatrixapp.tasks.presentation.uicomponents

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.theeisenhowermatrixapp.tasks.data.Task

@Composable
fun Quadrant(
    modifier: Modifier = Modifier,
    containerColor: Color,
    title: String,
    titleColor: Color,
    tasks: List<Task>,
    onTaskCheckedChange: (Int) -> Unit,
    onTaskClick: (Task)-> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        border = BorderStroke(1.dp, Color.Black)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 4.dp, vertical = 8.dp),
        ) {
            Text(
                text = title,
                modifier = Modifier.fillMaxWidth(),
                color = titleColor,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelLarge
            )

            HorizontalDivider(
                modifier = Modifier.padding(4.dp),
                thickness = 1.dp,
                color = titleColor
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(
                    items = tasks,
                    key = { it.id }
                ) { task ->
                    TaskUIItem(
                        task = task,
                        isDone = task.completed,
                        doneColor = titleColor,
                        onCheckedChange = {
                            onTaskCheckedChange(task.id)
                        },
                        onClick = { onTaskClick(task) }
                    )
                }
            }
        }
    }
}
