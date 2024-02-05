package com.example.color_app_remake.domain.repository

import com.example.color_app_remake.common.Resource
import com.example.color_app_remake.domain.model.Color
import kotlinx.coroutines.flow.Flow

interface ColorsRepository {

    suspend fun fetchColors(): Flow<Resource<List<Color>>>

}