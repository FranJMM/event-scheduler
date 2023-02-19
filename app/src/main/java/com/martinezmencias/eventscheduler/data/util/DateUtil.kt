package com.martinezmencias.eventscheduler.data.util

import android.annotation.SuppressLint
import com.martinezmencias.eventscheduler.data.server.Start
import com.martinezmencias.eventscheduler.domain.DateAndTime
import java.text.SimpleDateFormat
import java.util.*

fun Start?.parseStart(): DateAndTime? = this?.let {
    DateAndTime(date = it.localDate, time = it.localTime)
}

@SuppressLint("SimpleDateFormat")
fun String?.parseStartDateTime(): DateAndTime? = this?.parseDate()?.let { date ->
    val localDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date)
    val localTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(date)
    DateAndTime(date = localDate, time = localTime)
}

@SuppressLint("SimpleDateFormat")
fun String?.parseDate(): Date? = this?.let {
    SimpleDateFormat("yy-MM-dd'T'HH:mm:ss'Z'").parse(it)
}
