package com.example.color_app_remake.domain.usecase

import com.example.color_app_remake.common.Resource
import com.example.color_app_remake.domain.model.Color
import com.example.color_app_remake.domain.repository.ColorsRepository
import com.example.color_app_remake.ui.networkConnectivity.NetworkConnectivity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.math.log


class GetColorsUseCase @Inject constructor(
    private val repository: ColorsRepository
) {

    suspend operator fun invoke(): Flow<Resource<List<Color>>> = repository.fetchColors()


    suspend operator fun invoke(keyword: String) = repository.searchColors(keyword)
}