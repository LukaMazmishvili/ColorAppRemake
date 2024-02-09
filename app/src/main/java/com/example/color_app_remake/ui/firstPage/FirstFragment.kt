package com.example.color_app_remake.ui.firstPage

import android.os.Handler
import android.os.Looper
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.color_app_remake.common.base.BaseFragment
import com.example.color_app_remake.databinding.FragmentFirstBinding
import com.example.color_app_remake.ui.MainViewModel
import com.example.color_app_remake.ui.firstPage.adapter.ColorsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
@AndroidEntryPoint
class FirstFragment() : BaseFragment<FragmentFirstBinding>(
    FragmentFirstBinding::inflate
) {

    private val viewModel: MainViewModel by activityViewModels()

    private val adapter: ColorsAdapter by lazy {
        ColorsAdapter()
    }

    override fun started() {
        searchColors()
        setupViews()
    }

    override fun listeners() {
        adapter.onItemClickedListener = { item ->
            findNavController().navigate(
                FirstFragmentDirections.actionFirstFragmentToSecondFragment(
                    item
                )
            )
        }

        binding.root.setOnRefreshListener {
            viewModel.getColors()
        }
    }

    override fun observer() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.dataState.collect { data ->
                    adapter.submitList(data)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.isLoading.collect { isLoading ->
                    setupViews(isLoading)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.errorMsg.collect { errorMessage ->
                    if (viewModel.networkStatus.value != true && errorMessage.isNotEmpty()) {
                        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setupViews(isLoading: Boolean = false) {
        with(binding) {
            rvColorCards.adapter = adapter
            root.isRefreshing = isLoading
        }
    }

    private fun searchColors() {
        // todo search for debounce და გადააკეთე დებაუნსზე
        binding.sbColorSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                val searchText = newText ?: ""

                if (searchText.length > 2 || searchText.isEmpty()) {
                    viewModel.searchJob?.cancel()
                    viewModel.setSearchText(newText)
                    viewModel.getColors()
                }

                return true
            }

        })
    }
}