package com.example.color_app_remake.ui

import androidx.lifecycle.viewModelScope
import com.example.color_app_remake.common.Resource
import com.example.color_app_remake.common.base.BaseViewModel
import com.example.color_app_remake.common.extensions.filterAuthorColors
import com.example.color_app_remake.domain.model.Color
import com.example.color_app_remake.domain.usecase.GetColorsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class MainViewModel @Inject constructor(
    private val getColorsUseCase: GetColorsUseCase
) : BaseViewModel() {

    private val _dataState = MutableStateFlow<List<Color>>(emptyList())
    val dataState = _dataState.asStateFlow()

    private val _searchText = MutableStateFlow("")
    private val searchText = _searchText.asStateFlow()

    fun setSearchText(keyword: String?) {
        keyword?.let {
            _searchText.value = it
        }
    }

    init {
        getColors()
        searchColors()
    }

    private fun getColors() {
        viewModelScope.launch {
            // Collect Connection Status
            networkStatus.collect { connectionStatus ->
                when (connectionStatus) {
                    true -> getColorsUseCase.invoke().collect { response ->
                        when (response) {
                            is Resource.Success -> {
                                _dataState.value = response.data!!
                                hideLoading()
                            }

                            is Resource.Error -> {
                                setErrorMsg(response.errorMsg)
                                hideLoading()
                            }

                            is Resource.Loading -> showLoading()
                        }
                    }

                    false -> {
                        setErrorMsg("No Network Connection !")
                    }

                    null -> {}
                }
            }
        }
    }

    private fun searchColors() {
        viewModelScope.launch {
            searchText.debounce(400).collect { keyword ->
                if (keyword.length > 2) {
                    networkStatus.collect { connectionStatus ->
                        when (connectionStatus) {
                            true -> getColorsUseCase.invoke(keyword).collect { response ->
                                when (response) {
                                    is Resource.Success -> {
                                        // todo აქ რა გიწერია ამიხსენი მერე :დ --Done---
                                        _dataState.value = response.data!!.filterAuthorColors()
                                        hideLoading()
                                    }

                                    is Resource.Error -> {
                                        setErrorMsg(response.errorMsg)
                                        hideLoading()
                                    }

                                    is Resource.Loading -> showLoading()
                                }
                            }

                            false, null -> setErrorMsg("No Network Connection !")

                        }
                    }
                }
            }
        }
    }
}