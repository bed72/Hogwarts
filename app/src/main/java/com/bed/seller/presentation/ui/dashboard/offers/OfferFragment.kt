package com.bed.seller.presentation.ui.dashboard.offers

import android.os.Bundle
import android.view.View

import dagger.hilt.android.AndroidEntryPoint

import com.bed.seller.databinding.OfferFragmentBinding

import com.bed.seller.presentation.commons.fragments.BaseFragment
import com.bed.seller.presentation.commons.recyclers.getGenericAdapterOf
import com.bed.seller.presentation.commons.extensions.fragments.navigateTo
import com.bed.seller.presentation.commons.extensions.fragments.navigateBack

import com.bed.seller.presentation.ui.dashboard.offers.viewholder.ImageOfferViewHolder

@AndroidEntryPoint
class OfferFragment : BaseFragment<OfferFragmentBinding>(OfferFragmentBinding::inflate) {

    private val adapterImages by lazy {
        getGenericAdapterOf { ImageOfferViewHolder.create(it) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupComponents()
    }

    private fun setupComponents() {
        initAdapter()

        setupSave()
        setupCancel()
    }

    private fun initAdapter() {
        binding.imagesRecycler.run { adapter = adapterImages }
    }

    private fun setupSave() {
        binding.saveButton.setOnClickListener { navigateTo(OfferFragmentDirections.actionOfferToGallery()) }
    }

    private fun setupCancel() {
        binding.cancelButton.setOnClickListener { navigateBack() }
    }
}
