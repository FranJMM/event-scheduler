package com.martinezmencias.eventscheduler.ui.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.martinezmencias.eventscheduler.R
import com.martinezmencias.eventscheduler.databinding.FragmentListBinding
import com.martinezmencias.eventscheduler.ui.util.setVisible
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListFragment : Fragment(R.layout.fragment_list) {

    private val viewModel: ListViewModel by viewModel()

    private val adapter by lazy { EventsAdapter { event -> listState.onEventClicked(event)} }

    private lateinit var listState: ListState

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listState = this.createListState()

        val binding = FragmentListBinding.bind(view).apply {
            recycler.adapter = adapter
        }

        viewLifecycleOwner.launchAndCollect(viewModel.state) { state ->

            adapter.submitList(state.events)
            binding.recycler.setVisible(state.error == null)

            binding.error.text = state.error?.let { listState.errorToString(it) }
            binding.error.setVisible(state.error != null)
        }

        listState.requestLocationPermission {
            viewModel.onUiReady()
        }
    }

    fun <T> LifecycleOwner.launchAndCollect(
        flow: Flow<T>,
        state: Lifecycle.State = Lifecycle.State.STARTED,
        body: (T) -> Unit
    ) {
        lifecycleScope.launch {
            this@launchAndCollect.repeatOnLifecycle(state) {
                flow.collect(body)
            }
        }
    }

}