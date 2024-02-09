package com.example.color_app_remake.common.base

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel : ViewModel() {

    private val _isLoading = MutableSharedFlow<Boolean>()
    val isLoading = _isLoading.asSharedFlow()

    private val _errorMsg = MutableStateFlow<String>("")
    val errorMsg = _errorMsg.asStateFlow()

    private val _networkStatus = MutableStateFlow<Boolean?>(null)
    val networkStatus = _networkStatus.asStateFlow()

    protected fun setErrorMsg(errorMsg: String) {
        _errorMsg.value = errorMsg
    }

    protected suspend fun showLoading() {
        _isLoading.emit(true)
    }

    protected suspend fun hideLoading() {
        _isLoading.emit(false)
    }

    fun setNetworkStatus(networkStatus: Boolean?) {
        _networkStatus.value = networkStatus
    }

}