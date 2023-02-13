package com.martinezmencias.eventscheduler.ui.util

import android.content.Context
import com.martinezmencias.eventscheduler.R
import java.text.SimpleDateFormat
import java.util.*
import org.koin.java.KoinJavaComponent.getKoin

fun Date?.toReadableDate() = this?.let { date ->
    SimpleDateFormat("d MMM yyyy", Locale.getDefault()).format(date)
} ?: getKoin().get<Context>().getString(R.string.date_not_available)
