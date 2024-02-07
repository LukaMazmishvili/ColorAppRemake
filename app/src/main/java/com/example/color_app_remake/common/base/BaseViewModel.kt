package com.example.color_app_remake.common.base

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel : ViewModel() {

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errorMsg = MutableStateFlow<String>("")
    val errorMsg = _errorMsg.asStateFlow()

    private val _networkStatus = MutableStateFlow<Boolean?>(null)
    val networkStatus = _networkStatus.asStateFlow()

    protected fun setErrorMsg(errorMsg: String) {
        _errorMsg.value = errorMsg
    }

    protected fun showLoading() {
        _isLoading.value = true
    }

    protected fun hideLoading() {
        _isLoading.value = false
    }

    fun setNetworkStatus(networkStatus: Boolean?) {
        _networkStatus.value = networkStatus
    }

}