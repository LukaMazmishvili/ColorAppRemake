package com.example.color_app_remake.data.remote.mapper

import com.example.color_app_remake.data.remote.model.ColorDto
import com.example.color_app_remake.domain.model.Color

fun ColorDto.toColor(): Color {
    return Color(
        id = this.id,
        title = this.title,
        userName = this.userName,
        dateCreated = this.dateCreated,
        rgb = Color.RGB(
            red = this.rgb.red,
            green = this.rgb.green,
            blue = this.rgb.blue
        ),
        description = this.description,
        imageUrl = this.imageUrl
    )
}