package com.martinezmencias.eventscheduler.ui.detail.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import com.martinezmencias.eventscheduler.domain.Event
import com.martinezmencias.eventscheduler.domain.Price

class EventDetailInfoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    fun setEvent(event: Event) = event.apply {
        text = buildSpannedString {

            event.venue.let { venue ->
                bold { append("Venue: ") }
                appendLine(venue.name)
                appendLine("${venue.address}, ${venue.city}, ${venue.state}, ${venue.country}")
            }

            bold { append("Price: ") }
            appendLine(event.price.getPriceFormatted())
        }
    }

    private fun Price.getPriceFormatted() =
        if (min == max) {
            "$min $currency"
        } else {
            "$min - $max $currency"
        }
}