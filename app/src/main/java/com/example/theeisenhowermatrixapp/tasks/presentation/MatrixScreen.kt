package com.example.theeisenhowermatrixapp.tasks.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.theeisenhowermatrixapp.tasks.presentation.uicomponents.Quadrant
import com.example.theeisenhowermatrixapp.ui.theme.GrayTextSecondary
import com.example.theeisenhowermatrixapp.ui.theme.QuadrantGreen
import com.example.theeisenhowermatrixapp.ui.theme.QuadrantGreenText
import com.example.theeisenhowermatrixapp.ui.theme.QuadrantNeutral
import com.example.theeisenhowermatrixapp.ui.theme.QuadrantRed
import com.example.theeisenhowermatrixapp.ui.theme.QuadrantRedText
import com.example.theeisenhowermatrixapp.ui.theme.QuadrantYellow
import com.example.theeisenhowermatrixapp.ui.theme.QuadrantYellowText

@Composable
fun EisenhowerMatrixScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
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
                tasks = listOf(
                    "Сдать отчёт",
                    "Позвонить клиенту",
                    "Исправить критический баг"
                )
            )

            Quadrant(
                modifier = Modifier.weight(1f).fillMaxHeight(),
                containerColor = QuadrantGreen,
                title = "Важно, не срочно",
                titleColor = QuadrantGreenText,
                tasks = listOf(
                    "Изучить Compose",
                    "Начать спорт",
                    "Продумать цели на месяц"
                )
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
                tasks = listOf(
                    "Ответить на письма",
                    "Согласовать встречу"
                )
            )

            Quadrant(
                modifier = Modifier.weight(1f).fillMaxHeight(),
                containerColor = QuadrantNeutral,
                title = "Не срочно, не важно",
                titleColor = GrayTextSecondary,
                tasks = listOf(
                    "Посмотреть YouTube",
                    "Полистать соцсети"
                )
            )
        }
    }
}
