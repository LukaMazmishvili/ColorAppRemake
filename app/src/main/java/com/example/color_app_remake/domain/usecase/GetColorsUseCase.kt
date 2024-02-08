package com.example.color_app_remake.domain.usecase

import com.example.color_app_remake.common.Resource
import com.example.color_app_remake.domain.model.Color
import com.example.color_app_remake.domain.repository.ColorsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


@OptIn(ExperimentalCoroutinesApi::class)
class GetColorsUseCase @Inject constructor(
    private val repository: ColorsRepository
) {

    // todo გჭირდება აქ ორი მეთოდი ნაღდად?

    suspend operator fun invoke(keyword: String = ""): Flow<Resource<List<Color>>> = flow {

        if (keyword.isEmpty()) {
            emit(repository.fetchColors())
        } else {
            emit(repository.searchColors(keyword))
        }

    }.flatMapConcat {
        it
    }

//    suspend operator fun invoke(keyword: String) = repository.searchColors(keyword)
}