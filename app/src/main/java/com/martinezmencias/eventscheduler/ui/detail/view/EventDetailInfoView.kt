package com.martinezmencias.eventscheduler.ui.detail.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import com.martinezmencias.eventscheduler.data.util.convertToReadableText
import com.martinezmencias.eventscheduler.domain.Event

class EventDetailInfoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    fun setEvent(event: Event) = event.apply {
        text = buildSpannedString {

            event.startTime?.let { startTimeDate ->
                bold { append("Event date: ") }
                appendLine(startTimeDate.convertToReadableText())
            }

            event.salesStartTime?.let { salesStartTimeDate ->
                bold { append("Sales Event date: ") }
                appendLine(salesStartTimeDate.convertToReadableText())
            }
        }
    }
}