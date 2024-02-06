package com.example.color_app_remake.data.remote.service

import com.example.color_app_remake.data.remote.Endpoints.COLORS_ENDPOINT
import com.example.color_app_remake.data.remote.Endpoints.SEARCH_COLORS_ENDPOINT
import com.example.color_app_remake.data.remote.model.ColorDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ColorService {

    @GET(COLORS_ENDPOINT)
    suspend fun fetchData(): Response<List<ColorDto>>

    @GET(SEARCH_COLORS_ENDPOINT)
    suspend fun searchItem(
        @Query("keywords") keywords: String,
        @Query("format") format: String = "json"
    ): Response<List<ColorDto>>
}