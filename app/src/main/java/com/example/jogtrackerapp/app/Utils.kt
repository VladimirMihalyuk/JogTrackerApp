package com.example.jogtrackerapp.app

import java.text.SimpleDateFormat
import java.util.*




fun Int.parseToTime(): String{
    val formatter = SimpleDateFormat("HH:mm:ss", Locale.US)
    formatter.setTimeZone(TimeZone.getTimeZone("UTC"))
    return formatter.format(Date(this.toLong() * 1000))
}

fun Long.getDate(): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy")
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this * 1000
    return formatter.format(calendar.time)
}