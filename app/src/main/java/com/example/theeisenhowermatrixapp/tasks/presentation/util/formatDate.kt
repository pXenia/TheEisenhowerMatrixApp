package com.example.theeisenhowermatrixapp.tasks.presentation.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun formatDate(date: String): String {
    return try {
        val instant = Instant.parse(date)
        val zonedDateTime = instant.atZone(ZoneId.of("UTC"))
        val outputFormatter = DateTimeFormatter.ofPattern(
            "d MMM yyyy, HH:mm",
            Locale("ru")
        )

        zonedDateTime.format(outputFormatter)
    } catch (e: Exception) {
        e.printStackTrace()
        date
    }
}

