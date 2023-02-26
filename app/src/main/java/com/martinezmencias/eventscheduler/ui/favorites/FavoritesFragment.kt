package com.martinezmencias.eventscheduler.ui.favorites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.martinezmencias.eventscheduler.R
import com.martinezmencias.eventscheduler.databinding.FragmentFavoritesBinding
import com.martinezmencias.eventscheduler.databinding.FragmentListBinding
import com.martinezmencias.eventscheduler.domain.Event
import com.martinezmencias.eventscheduler.ui.common.adapters.EventsAdapter
import com.martinezmencias.eventscheduler.ui.common.launchAndCollect
import com.martinezmencias.eventscheduler.ui.util.setVisible
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    private val viewModel: FavoritesViewModel by viewModel()

    private val adapter by lazy { EventsAdapter { event ->
            favoritesState.onFavoriteEventClicked(event)
    } }

    private val favoritesState: FavoritesState by lazy { this.createFavoritestate() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentFavoritesBinding.bind(view).apply {
            recycler.adapter = adapter
        }

        binding.favoritesToolbar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        viewLifecycleOwner.launchAndCollect(viewModel.state) { state ->
            binding.handleUiState(state)
        }
    }

    private fun FragmentFavoritesBinding.handleUiState(state: FavoritesViewModel.UiState) {
        adapter.submitList(state.favoriteEvents)
        recycler.setVisible(state.error == null)

        favoritesEmptyMessage.setVisible(state.showFavoritesEmptyMessage)

        error.text = state.error?.let { favoritesState.errorToString(it) }
        error.setVisible(state.error != null)
    }
}