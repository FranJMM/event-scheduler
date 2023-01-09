package com.martinezmencias.eventscheduler.ui.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment

fun Fragment.createDetailState(context: Context = requireContext()) = DetailState(context)

class DetailState(private val context: Context) {
    fun openBuyTicketsUrl(url: String) {
        val urlIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(url)
        )
        context.startActivity(urlIntent)
    }
}