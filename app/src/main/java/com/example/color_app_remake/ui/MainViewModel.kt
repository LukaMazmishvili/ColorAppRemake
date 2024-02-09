package com.example.color_app_remake.ui

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.color_app_remake.common.Resource
import com.example.color_app_remake.common.base.BaseViewModel
import com.example.color_app_remake.common.extensions.filterAuthorColors
import com.example.color_app_remake.domain.model.Color
import com.example.color_app_remake.domain.usecase.GetColorsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    var searchJob: Job? = null

    fun setSearchText(keyword: String?) {
        keyword?.let {
            _searchText.value = it
        }
    }

    init {
        getColors()
    }

    fun getColors() {
        viewModelScope.launch {
            searchJob = CoroutineScope(Dispatchers.IO).launch {
                searchText.debounce(400L).collectLatest { keyword ->
                    networkStatus.collect { connectionStatus ->
                        when (connectionStatus) {
                            true -> getColorsUseCase.invoke(keyword).collect { response ->
                                when (response) {
                                    is Resource.Success -> {
                                        // todo აქ რა გიწერია ამიხსენი მერე :დ --Done---
                                        hideLoading()
                                        if (keyword.length > 2) {
                                            _dataState.value = response.data!!.filterAuthorColors()
                                        } else {
                                            _dataState.value = response.data!!
                                        }
                                    }

                                    is Resource.Error -> {
                                        hideLoading()
                                        setErrorMsg(response.errorMsg)
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