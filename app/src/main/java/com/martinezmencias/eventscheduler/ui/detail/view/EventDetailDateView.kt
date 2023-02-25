package com.martinezmencias.eventscheduler.ui.detail.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.martinezmencias.eventscheduler.R
import com.martinezmencias.eventscheduler.databinding.ViewDetailDateBinding
import com.martinezmencias.eventscheduler.domain.Event
import com.martinezmencias.eventscheduler.ui.util.toReadableDateAndTime

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

    fun setEventDateAndTime(event: Event, action:() -> Unit) {
        binding.dateTitle.text = resources.getString(R.string.event_date)
        binding.dateValue.text = event.startDateAndTime?.toReadableDateAndTime()
        binding.dateChip.setOnClickListener { action() }
    }

    fun setEventSalesDateAndTime(event: Event, action:() -> Unit) {
        binding.dateTitle.text = resources.getString(R.string.sales_date)
        binding.dateValue.text = event.salesDateAndTime?.toReadableDateAndTime()
        binding.dateChip.setOnClickListener { action() }
    }
}