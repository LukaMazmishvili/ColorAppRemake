package com.example.color_app_remake.common.extensions

import com.example.color_app_remake.domain.model.Color

fun List<Color>.filterAuthorColors(): List<Color> {
    val tempMap = mutableMapOf<String, Color>()
    this.map { tempMap[it.userName] = it }
    return tempMap.values.toList()
}