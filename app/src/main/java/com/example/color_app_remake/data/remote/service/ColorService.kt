package com.example.color_app_remake.data.remote.service

import com.example.color_app_remake.data.remote.model.ColorDto
import retrofit2.Response
import retrofit2.http.GET

interface ColorService {

    @GET("")
    suspend fun fetchData(): Response<List<ColorDto>>
}