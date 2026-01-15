package com.example.theeisenhowermatrixapp.tasks.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.theeisenhowermatrixapp.tasks.data.CreateTaskRequest
import com.example.theeisenhowermatrixapp.tasks.data.Task
import com.example.theeisenhowermatrixapp.ui.theme.AccentBlue
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskDialog(
    taskToEdit: Task? = null, // Добавлен параметр
    onDismiss: () -> Unit,
    onAddTask: (CreateTaskRequest) -> Unit,
    onUpdateTask: ((Int, CreateTaskRequest) -> Unit)? = null // Добавлен параметр
) {
    // Инициализация с данными существующей задачи или пустыми значениями
    var title by remember { mutableStateOf(taskToEdit?.title ?: "") }
    var description by remember { mutableStateOf(taskToEdit?.description ?: "") }
    var isImportant by remember { mutableStateOf(taskToEdit?.isImportant ?: false) }

    // Парсинг существующей даты, если редактируем задачу
    val initialDateTime = remember {
        taskToEdit?.deadlineAt?.let {
            try {
                val instant = Instant.parse(it)
                val zonedDateTime = instant.atZone(ZoneId.systemDefault())
                zonedDateTime.toLocalDate() to zonedDateTime.toLocalTime()
            } catch (e: Exception) {
                null to null
            }
        } ?: (null to null)
    }

    var selectedDate by remember { mutableStateOf<LocalDate?>(initialDateTime.first) }
    var selectedTime by remember { mutableStateOf<LocalTime?>(initialDateTime.second) }

    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    val deadlineText = remember(selectedDate, selectedTime) {
        when {
            selectedDate != null && selectedTime != null ->
                "${selectedDate} ${selectedTime}"
            selectedDate != null ->
                selectedDate.toString()
            else -> "Не выбран"
        }
    }

    // Диалог даты
    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = selectedDate?.atStartOfDay(ZoneId.systemDefault())?.toInstant()?.toEpochMilli()
        )

        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            colors = DatePickerDefaults.colors(
                containerColor = Color.White
            ),
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        selectedDate = Instant.ofEpochMilli(millis)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()

                        showTimePicker = true
                        showDatePicker = false
                    }
                }) {
                    Text("ОК", color = Color.Black)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Отмена", color = Color.Black)
                }
            }
        ) {
            DatePicker(
                state = datePickerState,
                colors = DatePickerDefaults.colors(
                    containerColor = Color.White
                )
            )
        }
    }

    // Диалог времени
    if (showTimePicker) {
        val timePickerState = rememberTimePickerState(
            initialHour = selectedTime?.hour ?: 0,
            initialMinute = selectedTime?.minute ?: 0
        )

        AlertDialog(
            onDismissRequest = { showTimePicker = false },
            containerColor = Color.White,
            confirmButton = {
                TextButton(onClick = {
                    selectedTime = LocalTime.of(
                        timePickerState.hour,
                        timePickerState.minute
                    )
                    showTimePicker = false
                }) {
                    Text("ОК", color = Color.Black)
                }
            },
            dismissButton = {
                TextButton(onClick = { showTimePicker = false }) {
                    Text("Отмена", color = Color.Black)
                }
            },
            text = {
                TimePicker(state = timePickerState)
            }
        )
    }

    // Основной диалог
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        title = {
            Text(
                text = if (taskToEdit != null) "Редактировать задачу" else "Новая задача",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = AccentBlue,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Название") },
                    shape = RoundedCornerShape(20.dp),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Описание") },
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = deadlineText,
                    onValueChange = {},
                    label = { Text("Срок") },
                    readOnly = true,
                    enabled = false,
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                TextButton(
                    onClick = { showDatePicker = true },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(
                        text = "Выбрать дату",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = isImportant,
                        onCheckedChange = { isImportant = it },
                        colors = CheckboxDefaults.colors(
                            checkedColor = AccentBlue,
                            uncheckedColor = AccentBlue
                        )
                    )
                    Text(
                        text = "Важно",
                        color = AccentBlue,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }
        },
        confirmButton = {
            TextButton(
                enabled = title.isNotBlank()
                        && selectedDate != null
                        && selectedTime != null,
                onClick = {
                    if (taskToEdit != null && onUpdateTask != null) {
                        // Редактирование существующей задачи
                        onUpdateTask(
                            taskToEdit.id,
                            CreateTaskRequest(
                                title = title,
                                description = description,
                                isImportant = isImportant,
                                deadlineAt = toIsoString(
                                    selectedDate!!,
                                    selectedTime!!
                                )
                            )
                        )
                    } else {
                        // Создание новой задачи
                        onAddTask(
                            CreateTaskRequest(
                                title = title,
                                description = description,
                                isImportant = isImportant,
                                deadlineAt = toIsoString(
                                    selectedDate!!,
                                    selectedTime!!
                                )
                            )
                        )
                    }
                }
            ) {
                Text("Сохранить", color = AccentBlue)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Отмена", color = Color.Gray)
            }
        }
    )
}


@RequiresApi(Build.VERSION_CODES.O)
fun toIsoString(date: LocalDate, time: LocalTime): String {
    return date
        .atTime(time)
        .atOffset(ZoneOffset.UTC)
        .toString()
}
