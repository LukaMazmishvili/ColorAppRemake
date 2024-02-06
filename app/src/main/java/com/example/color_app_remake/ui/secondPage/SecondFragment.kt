package com.example.color_app_remake.ui.secondPage

import android.graphics.Color
import android.graphics.ColorSpace.Rgb
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.color_app_remake.R
import com.example.color_app_remake.common.base.BaseFragment
import com.example.color_app_remake.common.extensions.formatDate
import com.example.color_app_remake.common.extensions.uploadImage
import com.example.color_app_remake.common.extensions.uploadImageByUrl
import com.example.color_app_remake.databinding.FragmentSecondBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SecondFragment : BaseFragment<FragmentSecondBinding>(
    FragmentSecondBinding::inflate
) {

    private val args: SecondFragmentArgs by navArgs()

    override fun started() {
        setData()
    }

    override fun listeners() {

    }

    private fun setData() {
        val color = args.color

        with(binding) {
            ivColorImage.uploadImageByUrl(color.imageUrl)
            tvAuthor.text = color.userName
            tvDate.text = color.dateCreated.formatDate()
            tvTitle.text = color.title
            tvRGBColor.setBackgroundColor(Color.rgb(color.rgb.red, color.rgb.green, color.rgb.blue))
        }
    }

}