package com.android.komiteebicicycle.core.utils

import android.app.DatePickerDialog
import android.content.Context
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

// Utility function to parse date string
fun parseDate(date: String): Date? {
    return try {
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(date)
    } catch (e: Exception) {
        null
    }
}

// Utility function to show date picker dialog
fun showDatePickerDialog(context: Context, onDateSelected: (String) -> Unit) {
    val calendar = Calendar.getInstance()
    DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val selectedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
            onDateSelected(selectedDate)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).show()
}

fun getMonthsForBici(startDate: String, endDate: String): List<String> {
    val dateFormat = SimpleDateFormat("yyyy-MM", Locale.getDefault())
    val start = dateFormat.parse(startDate)
    val end = dateFormat.parse(endDate)

    val calendar = Calendar.getInstance()
    calendar.time = start

    val months = mutableListOf<String>()
    while (calendar.time.before(end) || calendar.time == end) {
        months.add(dateFormat.format(calendar.time))
        calendar.add(Calendar.MONTH, 1)
    }
    return months
}