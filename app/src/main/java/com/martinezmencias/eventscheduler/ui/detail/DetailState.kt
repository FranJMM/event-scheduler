package com.martinezmencias.eventscheduler.ui.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.CalendarContract
import androidx.fragment.app.Fragment
import com.martinezmencias.eventscheduler.domain.Event
import java.util.*

fun Fragment.createDetailState(
    context: Context = requireContext()
) = DetailState(context)

class DetailState(private val context: Context) {
    fun openBuyTicketsUrl(url: String) {
        val urlIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(url)
        )
        context.startActivity(urlIntent)
    }

    fun addCalendarEvent(event: Event?) {
        event?.startTime?.let {
            addCalendarDate(event.name, it)
        }
    }

    fun addCalendarSales(event: Event?) {
        event?.salesStartTime?.let {
            addCalendarDate("Venta de entradas: ${event.name}", it)
        }
    }

    private fun addCalendarDate(title: String, date: Date) {
        val intent = Intent(Intent.ACTION_INSERT)
            .setData(CalendarContract.Events.CONTENT_URI)
            .putExtra(CalendarContract.Events.TITLE, title)
            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, date.time)
        context.startActivity(intent)
    }
}