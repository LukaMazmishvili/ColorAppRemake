package com.example.color_app_remake.ui.firstPage

import androidx.lifecycle.viewModelScope
import com.example.color_app_remake.common.Resource
import com.example.color_app_remake.common.base.BaseViewModel
import com.example.color_app_remake.common.extensions.filterAuthorColors
import com.example.color_app_remake.domain.model.Color
import com.example.color_app_remake.domain.usecase.GetColorsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Collections
import javax.inject.Inject

@HiltViewModel
class FirstFragmentVM @Inject constructor(private val getColorsUseCase: GetColorsUseCase) :
    BaseViewModel() {

    private val _dataState = MutableStateFlow<List<Color>>(emptyList())
    val dataState = _dataState.asStateFlow()

    init {
        getColors()
    }

    private fun getColors() {
        viewModelScope.launch {
            getColorsUseCase.invoke().collect { response ->
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
        }
    }

    fun searchColors(keyword: String) {
        viewModelScope.launch {
            getColorsUseCase.invoke(keyword).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _dataState.value =
                            if (keyword.length > 2) {
                                response.data!!.filterAuthorColors()
                            } else {
                                response.data!!
                            }
                        hideLoading()
                    }

                    is Resource.Error -> {
                        setErrorMsg(response.errorMsg)
                        hideLoading()
                    }

                    is Resource.Loading -> showLoading()
                }
            }
        }
    }

    fun filterAuthorColors(list: List<Color>): List<Color> {
        return list.groupBy { it.userName }.mapValues { (_, items) -> items.last() }.values.toList()
    }

}