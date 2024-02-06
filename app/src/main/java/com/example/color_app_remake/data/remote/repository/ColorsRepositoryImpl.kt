package com.example.color_app_remake.data.remote.repository

import com.example.color_app_remake.common.Resource
import com.example.color_app_remake.data.remote.mapper.toColor
import com.example.color_app_remake.data.remote.service.ColorService
import com.example.color_app_remake.domain.model.Color
import com.example.color_app_remake.domain.repository.ColorsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ColorsRepositoryImpl @Inject constructor(
    private val colorService: ColorService
) : ColorsRepository {
    override suspend fun fetchColors(): Flow<Resource<List<Color>>> = flow {
        try {
            emit(Resource.Loading(true))

            val response = colorService.fetchData()

            if (response.isSuccessful) {
                val data = response.body()
                emit(Resource.Success(data?.map { it.toColor() }))
            } else {
                emit(Resource.Error("Something Went Wrong !"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }

    override suspend fun searchColors(keyword: String): Flow<Resource<List<Color>>> = flow {
        try {
            emit(Resource.Loading(true))

            val response = colorService.searchItem(keyword)

            if (response.isSuccessful) {
                val data = response.body()
                emit(Resource.Success(data?.map { it.toColor() }))
            } else {
                emit(Resource.Error("Something Went Wrong !"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }
}