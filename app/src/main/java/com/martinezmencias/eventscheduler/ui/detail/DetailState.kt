package com.martinezmencias.eventscheduler.ui.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.CalendarContract
import androidx.fragment.app.Fragment
import com.martinezmencias.eventscheduler.domain.Event

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

    fun addCalendarEvent(event: Event) {
        val intent = Intent(Intent.ACTION_INSERT)
            .setData(CalendarContract.Events.CONTENT_URI)
            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, event.startTime?.time)
            .putExtra(CalendarContract.Events.TITLE, event.name)
        context.startActivity(intent)
    }
}