package com.martinezmencias.eventscheduler.data.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun String?.parseDate() = this?.let {
    SimpleDateFormat("yy-MM-dd'T'HH:mm:ss'Z'").parse(it)
}

@SuppressLint("SimpleDateFormat")
fun Date.convertToReadableText(): String =
    SimpleDateFormat("EEE, d MMM yyyy HH:mm").format(this)

