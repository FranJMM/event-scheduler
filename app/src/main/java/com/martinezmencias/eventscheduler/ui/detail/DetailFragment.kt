package com.martinezmencias.eventscheduler.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.martinezmencias.eventscheduler.R
import com.martinezmencias.eventscheduler.databinding.FragmentDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val viewModel: DetailViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDetailBinding.bind(view)
    }
}