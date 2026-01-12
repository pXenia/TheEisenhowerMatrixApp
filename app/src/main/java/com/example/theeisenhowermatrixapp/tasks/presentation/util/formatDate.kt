package com.example.theeisenhowermatrixapp.tasks.presentation.util

import android.os.Build
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

fun formatDate(date: String): String {
    return try {
        val inputFormatter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter.ISO_DATE_TIME
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        val outputFormatter = DateTimeFormatter.ofPattern(
            "d MMM yyyy",
            Locale("ru")
        )

        LocalDate.parse(date, inputFormatter).format(outputFormatter)
    } catch (e: Exception) {
        date
    }
}

