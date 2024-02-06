package com.example.color_app_remake.domain.usecase

import com.example.color_app_remake.domain.repository.ColorsRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class GetColorsUseCase @Inject constructor(private val repository: ColorsRepository) {

    suspend operator fun invoke() = repository.fetchColors()
    suspend operator fun invoke(keyword: String) = repository.searchColors(keyword)
}