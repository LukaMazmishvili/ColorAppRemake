package com.example.color_app_remake.data.remote.mapper

import com.example.color_app_remake.data.remote.model.ColorDto
import com.example.color_app_remake.domain.model.Color

fun ColorDto.toColor(): Color {
    return Color(
        colorFullName = colorName + colorSurname
    )
}