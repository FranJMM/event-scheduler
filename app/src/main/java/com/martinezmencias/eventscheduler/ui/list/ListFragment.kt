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
import com.martinezmencias.eventscheduler.ui.common.adapters.EventsAdapter
import com.martinezmencias.eventscheduler.ui.common.launchAndCollect
import com.martinezmencias.eventscheduler.ui.util.setVisible
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListFragment : Fragment(R.layout.fragment_list) {

    private val viewModel: ListViewModel by viewModel()

    private val adapter by lazy { EventsAdapter { event -> listState.onEventClicked(event)} }

    private val listState: ListState by lazy { this.createListState() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentListBinding.bind(view).apply {
            recycler.adapter = adapter
        }

        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_go_favorites -> {
                    listState.onFavoritesClicked()
                    true
                }
                else -> false
            }
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

        loading.setVisible(state.loading)

        error.text = state.error?.let { listState.errorToString(it) }
        error.setVisible(state.error != null)
    }
}