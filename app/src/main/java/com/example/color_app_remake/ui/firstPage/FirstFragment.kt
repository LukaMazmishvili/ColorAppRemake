package com.example.color_app_remake.ui.firstPage

import android.graphics.Color
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.color_app_remake.common.base.BaseFragment
import com.example.color_app_remake.databinding.FragmentFirstBinding
import com.example.color_app_remake.ui.firstPage.adapter.ColorsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FirstFragment() : BaseFragment<FragmentFirstBinding>(
    FragmentFirstBinding::inflate
) {

    private val viewModel: FirstFragmentVM by viewModels()

    private val adapter: ColorsAdapter by lazy {
        ColorsAdapter()
    }

    override fun started() {

    }

    override fun listeners() {

    }

    override fun observer() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.dataState.collect { data ->
                    adapter.submitList(data)
                }
            }
        }
    }

}