package com.example.color_app_remake.ui.firstPage.adapter

import com.example.color_app_remake.common.base.BaseAdapter
import com.example.color_app_remake.databinding.ItemColorBinding
import com.example.color_app_remake.domain.model.Color

class ColorsAdapter : BaseAdapter<Color, ItemColorBinding>(ItemColorBinding::inflate) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        with(holder.binding) {

        }

    }
}