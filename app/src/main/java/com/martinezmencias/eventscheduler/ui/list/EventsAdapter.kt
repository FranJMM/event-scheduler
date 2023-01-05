package com.martinezmencias.eventscheduler.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.martinezmencias.eventscheduler.databinding.ViewEventBinding

class EventsAdapter :
    ListAdapter<com.martinezmencias.eventscheduler.domain.Event, EventsAdapter.ViewHolder>(basicDiffUtil { old, new ->
        old.name == new.name }
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = getItem(position)
        holder.binding.eventTitle.text = event.name
        Glide.with(holder.binding.eventImage).load(event.imageUrl).into(holder.binding.eventImage)
    }

    class ViewHolder(val binding: ViewEventBinding) : RecyclerView.ViewHolder(binding.root)
}

inline fun <T> basicDiffUtil(
    crossinline areItemsTheSame: (T, T) -> Boolean = { old, new -> old == new },
    crossinline areContentsTheSame: (T, T) -> Boolean = { old, new -> old == new }
) = object : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
        areItemsTheSame(oldItem, newItem)

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
        areContentsTheSame(oldItem, newItem)
}
