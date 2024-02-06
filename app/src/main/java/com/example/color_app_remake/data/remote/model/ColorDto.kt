package com.example.color_app_remake.data.remote.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class ColorDto(
    val id: Long,
    val title: String,
    val userName: String,
    val numViews: Int,
    val numVotes: Int,
    val numComments: Int,
    val numHearts: Int,
    val rank: Int,
    val dateCreated: String,
    val hex: String,
    val rgb: RGB,
    val hsv: HSV,
    val description: String,
    val url: String,
    val imageUrl: String,
    val badgeUrl: String,
    val apiUrl: String
) {

    data class RGB(
        val red: Int,
        val green: Int,
        val blue: Int
    )

    data class HSV(
        val hue: Int,
        val saturation: Int,
        val value: Int,
    )
}
