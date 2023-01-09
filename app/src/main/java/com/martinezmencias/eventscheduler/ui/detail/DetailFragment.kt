package com.martinezmencias.eventscheduler.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.martinezmencias.eventscheduler.R
import com.martinezmencias.eventscheduler.databinding.FragmentDetailBinding
import com.martinezmencias.eventscheduler.ui.common.launchAndCollect
import com.martinezmencias.eventscheduler.ui.util.setVisible
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val safeArgs: DetailFragmentArgs by navArgs()

    private val viewModel: DetailViewModel by viewModel { parametersOf(safeArgs.eventId) }

    private val detailState: DetailState by lazy { this.createDetailState() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentDetailBinding.bind(view)

        viewLifecycleOwner.launchAndCollect(viewModel.state) { state ->
            binding.handleUiState(state)
        }
    }

    private fun FragmentDetailBinding.handleUiState(state: DetailViewModel.UiState) {
        state.event?.let { event ->
            Glide.with(eventImage).load(event.imageUrl).into(eventImage)
            eventNameText.text = event.name
            eventBuyTicketsButton.setBuyTicketsButton(event.salesUrl)
            eventDetailInfoView.setEvent(event)
        }
    }

    private fun Button.setBuyTicketsButton(salesUrl: String?) {
        if (salesUrl != null) {
            setOnClickListener { detailState.openBuyTicketsUrl(salesUrl) }
        } else {
            setVisible(false)
        }
    }
}
