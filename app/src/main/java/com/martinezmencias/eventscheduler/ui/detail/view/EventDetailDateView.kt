package com.martinezmencias.eventscheduler.ui.detail.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.martinezmencias.eventscheduler.R
import com.martinezmencias.eventscheduler.data.util.convertToReadableText
import com.martinezmencias.eventscheduler.databinding.ViewDetailDateBinding
import com.martinezmencias.eventscheduler.domain.Event
import java.util.*

class EventDetailDateView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding: ViewDetailDateBinding

    init {
        val view = inflate(context, R.layout.view_detail_date, this)
        binding = ViewDetailDateBinding.bind(view)
    }

    fun setEventDate(event: Event, action:() -> Unit) {
        binding.dateTitle.text = "Date"
        binding.dateValue.text = event.startTime?.convertToReadableText()
        binding.dateChip.setOnClickListener { action() }
    }

    fun setEventSalesDate(event: Event, action:() -> Unit) {
        binding.dateTitle.text = "Sales"
        binding.dateValue.text = event.salesStartTime?.convertToReadableText()
        binding.dateChip.setOnClickListener { action() }
    }
}