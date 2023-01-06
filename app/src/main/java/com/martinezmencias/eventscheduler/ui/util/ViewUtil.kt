package com.martinezmencias.eventscheduler.ui.util

import android.view.View

fun View.setVisible(visible: Boolean?) {
    visibility = if (visible == true) View.VISIBLE else View.GONE
}