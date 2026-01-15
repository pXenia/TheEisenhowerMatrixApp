package com.example.theeisenhowermatrixapp.tasks.presentation.uicomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.theeisenhowermatrixapp.tasks.domain.Task
import com.example.theeisenhowermatrixapp.tasks.presentation.util.formatDate
import com.example.theeisenhowermatrixapp.ui.theme.GrayTextSecondary
import com.example.theeisenhowermatrixapp.ui.theme.QuadrantGreen
import com.example.theeisenhowermatrixapp.ui.theme.QuadrantGreenText
import com.example.theeisenhowermatrixapp.ui.theme.QuadrantNeutral
import com.example.theeisenhowermatrixapp.ui.theme.QuadrantRed
import com.example.theeisenhowermatrixapp.ui.theme.QuadrantRedText
import com.example.theeisenhowermatrixapp.ui.theme.QuadrantYellow


@Composable
fun TaskItem(
    task: Task,
    onClick: () -> Unit,
    onCheckedChange: (Boolean) -> Unit,
    isDone: Boolean,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth().clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = taskContainerColor(task)
        ),
    ) {
        Column(modifier = Modifier.padding(12.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = task.completed,
                    onCheckedChange = onCheckedChange,
                    colors = CheckboxDefaults.colors(
                        uncheckedColor = Color.Black
                    )
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = task.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                    textDecoration = if (isDone)
                        TextDecoration.LineThrough
                    else
                        TextDecoration.None
                )

                IconButton(onClick = onDeleteClick) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Удалить",
                        tint = QuadrantRedText
                    )
                }
            }

            if (task.description.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = task.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = GrayTextSecondary
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (task.isImportant) {
                        Tag(text = "Важно", color = QuadrantGreenText)
                    }
                    if (task.isUrgent) {
                        Tag(text = "Срочно", color = QuadrantRedText)
                    }
                }

                task.deadlineAt?.let {
                    DeadlineText(formatDate(it))
                }
            }
        }
    }
}


@Composable
fun DeadlineText(date: String) {
    Text(
        text = "до $date",
        fontSize = 14.sp,
        style = MaterialTheme.typography.headlineSmall
    )
}

@Composable
fun taskContainerColor(task: Task): Color {
    return when (task.quadrant) {
        "Q1" -> QuadrantRed
        "Q2" -> QuadrantGreen
        "Q3" -> QuadrantYellow
        "Q4" -> QuadrantNeutral
        else -> MaterialTheme.colorScheme.surfaceVariant
    }
}


@Composable
fun Tag(
    text: String,
    color: Color
) {
    Box(
        modifier = Modifier
            .background(
                color = color.copy(alpha = 0.15f),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 8.dp, vertical = 2.dp)
    ) {
        Text(
            text = text,
            color = color,
            fontSize = 12.sp
        )
    }
}
