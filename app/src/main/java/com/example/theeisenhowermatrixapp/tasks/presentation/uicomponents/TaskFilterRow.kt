package com.example.theeisenhowermatrixapp.tasks.presentation.uicomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.theeisenhowermatrixapp.tasks.presentation.util.TaskFilter
import com.example.theeisenhowermatrixapp.ui.theme.AccentBlue
import com.example.theeisenhowermatrixapp.ui.theme.BlackText
import com.example.theeisenhowermatrixapp.ui.theme.GraySurface

@Composable
fun TaskFilterRow(
    selectedFilter: TaskFilter,
    onFilterSelected: (TaskFilter) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FilterBadge(
            text = "Все",
            selected = selectedFilter == TaskFilter.ALL,
            onClick = { onFilterSelected(TaskFilter.ALL) }
        )
        FilterBadge(
            text = "Q1",
            selected = selectedFilter == TaskFilter.Q1,
            onClick = { onFilterSelected(TaskFilter.Q1) }
        )
        FilterBadge(
            text = "Q2",
            selected = selectedFilter == TaskFilter.Q2,
            onClick = { onFilterSelected(TaskFilter.Q2) }
        )
        FilterBadge(
            text = "Q3",
            selected = selectedFilter == TaskFilter.Q3,
            onClick = { onFilterSelected(TaskFilter.Q3) }
        )
        FilterBadge(
            text = "Q4",
            selected = selectedFilter == TaskFilter.Q4,
            onClick = { onFilterSelected(TaskFilter.Q4) }
        )
    }
}

@Composable
private fun FilterBadge(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val background = if (selected) AccentBlue else GraySurface
    val textColor = if (selected) Color.White else BlackText

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(background)
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            color = textColor
        )
    }
}
