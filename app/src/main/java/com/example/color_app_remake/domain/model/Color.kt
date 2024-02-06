package com.example.color_app_remake.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Color(
    val id: Long,
    val title: String,
    val userName: String,
    val dateCreated: String,
    val rgb: RGB,
    val description: String,
    val imageUrl: String
) : Parcelable {
    @Parcelize
    data class RGB(
        val red: Int,
        val green: Int,
        val blue: Int
    ) : Parcelable
}