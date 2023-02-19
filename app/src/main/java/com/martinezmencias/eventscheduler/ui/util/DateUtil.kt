package com.martinezmencias.eventscheduler.ui.util

import android.annotation.SuppressLint
import android.content.Context
import com.martinezmencias.eventscheduler.R
import com.martinezmencias.eventscheduler.domain.DateAndTime
import java.text.SimpleDateFormat
import java.util.*
import org.koin.java.KoinJavaComponent.getKoin

@SuppressLint("SimpleDateFormat")
fun DateAndTime?.toReadableDate() = this?.date?.let { dateString ->
    val date = SimpleDateFormat("yy-MM-dd").parse(dateString)
    date?.let {
        SimpleDateFormat("d MMM yyyy", Locale.getDefault()).format(it)
    }
} ?: getKoin().get<Context>().getString(R.string.date_not_available)

@SuppressLint("SimpleDateFormat")
fun DateAndTime?.toReadableDateAndTime() = this?.let { dateAndTime ->
    if (dateAndTime.time != null) {
        val dateAndTimeAsString = "${dateAndTime.date}-${dateAndTime.time}"
        val date = SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").parse(dateAndTimeAsString)
        if (date != null) {
            SimpleDateFormat("d MMM yyyy HH:mm", Locale.getDefault()).format(date)
        } else {
            dateAndTime.toReadableDate()
        }
    } else {
        dateAndTime.toReadableDate()
    }
}

@SuppressLint("SimpleDateFormat")
fun DateAndTime?.toDate(): Date? = this?.let { dateAndTime ->
    when {
        dateAndTime.date != null && dateAndTime.time != null -> SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").parse(
            "${dateAndTime.date}-${dateAndTime.time}"
        )
        dateAndTime.date != null -> SimpleDateFormat("yyyy-MM-dd").parse(dateAndTime.date!!)
        else -> null
    }
}
