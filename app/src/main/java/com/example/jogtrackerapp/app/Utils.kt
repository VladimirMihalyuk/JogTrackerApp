package com.example.jogtrackerapp.app

import java.text.SimpleDateFormat
import java.util.*


val locale = Locale.US

fun Int.parseToTime(): String{
    val formatter = SimpleDateFormat("HH:mm:ss",locale)
    formatter.setTimeZone(TimeZone.getTimeZone("UTC"))
    return formatter.format(Date(this.toLong() * 1000))
}

fun Long.getDate(): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", locale)
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    return formatter.format(calendar.time)
}

fun String.dateToMillis(): Long{
    val formatter = SimpleDateFormat("dd/MM/yyyy", locale)
    return formatter.parse(this)?.time ?:0
}