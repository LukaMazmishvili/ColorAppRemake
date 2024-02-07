package com.example.color_app_remake.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.color_app_remake.ColorApp
import com.example.color_app_remake.R
import com.example.color_app_remake.databinding.ActivityMainBinding
import com.example.color_app_remake.ui.networkConnectivity.ConnectivityObserver
import com.example.color_app_remake.ui.networkConnectivity.NetworkConnectivityObserver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var networkConnectivityObserver: ConnectivityObserver

    private val viewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    private var networkStatus = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        networkObserver()
        listeners()

    }

    private fun listeners() {
        binding.btnGetData.setOnClickListener {
            hideButton()
            viewModel.setNetworkStatus(null)
        }
    }

    private fun hideButton() {
        with(binding) {
            btnGetData.visibility = View.GONE
            navHost.visibility = View.VISIBLE
        }
    }

    private fun showButton() {
        with(binding) {
            if (viewModel.dataState.value.isEmpty()) {
                btnGetData.visibility = View.VISIBLE
                navHost.visibility = View.GONE
            }
        }
    }

    private fun networkObserver() {
        lifecycleScope.launch {
            networkConnectivityObserver.observe().collect { networkStatus ->

                Log.d("NetworkInActivity", "networkObserver: $networkStatus")
                when (networkStatus) {
                    ConnectivityObserver.Status.Lost, ConnectivityObserver.Status.Unavailable -> {
                        viewModel.setNetworkStatus(false)
                        showButton()
                    }

                    ConnectivityObserver.Status.Available -> {
                        viewModel.setNetworkStatus(true)
                        hideButton()
                    }

                    else -> {
                        // DoesNothing
                    }
                }
            }
        }
    }
}