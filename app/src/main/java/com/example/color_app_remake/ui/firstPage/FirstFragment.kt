package com.example.color_app_remake.ui.firstPage

import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.color_app_remake.common.base.BaseFragment
import com.example.color_app_remake.databinding.FragmentFirstBinding
import com.example.color_app_remake.domain.repository.ColorsRepository
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


    private val searchHandler = Handler(Looper.getMainLooper())
    private var searchRunnable: Runnable? = null
    private val searchDelayMillis = 400

    override fun started() {
        setupViews()
        searchColors()
    }

    fun test(list: List<com.example.color_app_remake.domain.model.Color>) {

        val testMap = mutableMapOf<String, com.example.color_app_remake.domain.model.Color>()
        list
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

                    test(data)
                    Log.d("dataInFragment", "observer: $data")
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
                viewModel.errorMsg.collect {
                    Log.d("error", "observer: ${it}")
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
                    viewModel.searchColors(newText.toString())
                }
                searchRunnable?.let { searchHandler.postDelayed(it, searchDelayMillis.toLong()) }

                return true
            }

        })
    }
}