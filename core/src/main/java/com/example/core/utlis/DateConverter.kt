package com.example.core.utlis

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object DateConverter {
    private val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())

    fun convertStringToMillis(dateString: String): Long {
        val date = inputFormat.parse(dateString)
        return date?.time ?: 0L
    }

    fun convertMillisToString(timeMillis: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeMillis
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return sdf.format(calendar.time)
    }
}
