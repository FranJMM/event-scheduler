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
import com.martinezmencias.eventscheduler.ui.common.launchAndCollect
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
            binding.handleUiState(state)
        }

        listState.requestLocationPermission {
            viewModel.onUiReady()
        }
    }

    private fun FragmentListBinding.handleUiState(state: ListViewModel.UiState) {
        adapter.submitList(state.events)
        recycler.setVisible(state.error == null)

        error.text = state.error?.let { listState.errorToString(it) }
        error.setVisible(state.error != null)
    }
}