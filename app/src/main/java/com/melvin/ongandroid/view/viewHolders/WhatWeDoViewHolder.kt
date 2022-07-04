package com.melvin.ongandroid.view.viewHolders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.melvin.ongandroid.databinding.FragmentTestimonialsItemBinding
import com.melvin.ongandroid.model.entities.whatWeDo.WhatWeDo
import com.squareup.picasso.Picasso
import javax.inject.Inject

class WhatweDoViewHolder @Inject constructor(view: View): RecyclerView.ViewHolder(view) {

    val binding = FragmentTestimonialsItemBinding.bind(view)

    fun drawWhatWeDo(whatWeDo: WhatWeDo) {
        binding.tvActivitieTitle.text = whatWeDo.name
        binding.tvActivitieDescription.text = whatWeDo.rawDescription
        Picasso.get().load(whatWeDo.image).into(binding.ivActivitieImage)
    }
}