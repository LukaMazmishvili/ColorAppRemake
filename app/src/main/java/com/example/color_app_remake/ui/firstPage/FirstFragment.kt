package com.example.color_app_remake.ui.firstPage

import android.os.Handler
import android.os.Looper
import android.util.Log
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
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FirstFragment() : BaseFragment<FragmentFirstBinding>(
    FragmentFirstBinding::inflate
) {

    private val viewModel: MainViewModel by activityViewModels()

    private val adapter: ColorsAdapter by lazy {
        ColorsAdapter()
    }


    private val searchHandler = Handler(Looper.getMainLooper())
    private var searchRunnable: Runnable? = null
    private val searchDelayMillis = 400

    override fun started() {
        setupViews()
        searchColors()
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
            observer()
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

    private fun setupViews(isLoading: Boolean = true) {
        with(binding) {
            rvColorCards.adapter = adapter
            root.isRefreshing = isLoading
        }
    }

    private fun searchColors() {
        binding.sbColorSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchRunnable?.let { searchHandler.removeCallbacks(it) }

                searchRunnable = Runnable {
                    if (!newText.isNullOrEmpty())
                        viewModel.searchColors(newText.toString())
                }
                searchRunnable?.let { searchHandler.postDelayed(it, searchDelayMillis.toLong()) }

                return true
            }

        })
    }
}