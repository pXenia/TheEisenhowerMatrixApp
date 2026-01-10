package com.example.theeisenhowermatrixapp.tasks.presentation.uicomponents

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
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
import com.example.theeisenhowermatrixapp.ui.theme.AccentBlue
import com.example.theeisenhowermatrixapp.ui.theme.AccentBlueDark
import com.example.theeisenhowermatrixapp.ui.theme.BlackText
import com.example.theeisenhowermatrixapp.ui.theme.GrayTextSecondary

@Composable
fun TaskUIItem(
    title: String,
    isDone: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    doneColor: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        color = Color.Transparent,
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
                    uncheckedColor = GrayTextSecondary.copy(alpha = 0.6f)
                )
            )

            Text(
                text = title,
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

