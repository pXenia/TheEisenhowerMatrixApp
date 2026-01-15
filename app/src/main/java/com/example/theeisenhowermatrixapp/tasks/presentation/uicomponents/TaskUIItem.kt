package com.example.theeisenhowermatrixapp.tasks.presentation.uicomponents

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.theeisenhowermatrixapp.tasks.data.Task

@Composable
fun TaskUIItem(
    task: Task,
    isDone: Boolean,
    onClick: () -> Unit,
    onCheckedChange: (Boolean) -> Unit,
    doneColor: Color,
) {
    Surface(
        modifier = Modifier.padding(horizontal = 4.dp).clickable{ onClick() },
        color = Color.Transparent,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, doneColor.copy(alpha = 0.3f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Checkbox(
                checked = isDone,
                onCheckedChange = onCheckedChange,
                colors = CheckboxDefaults.colors(
                    checkedColor = doneColor,
                    uncheckedColor = doneColor.copy(alpha = 0.4f)
                )
            )
            Column {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (isDone)
                        MaterialTheme.colorScheme.onSurfaceVariant
                    else
                        MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    textDecoration = if (isDone)
                        TextDecoration.LineThrough
                    else
                        TextDecoration.None
                )
            }
        }
    }
}


