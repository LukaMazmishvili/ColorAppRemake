package com.example.color_app_remake.common.extensions

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

fun String.formatDate(): String {

    val mapOfMonths = mapOf<Int, String>(
        1 to "იან",
        2 to "თებ",
        3 to "მარ",
        4 to "აპრ",
        5 to "მაი",
        6 to "ივნ",
        7 to "ივლ",
        8 to "აგვ",
        9 to "სექ",
        10 to "ოქტ",
        11 to "ნოე",
        12 to "დეკ"
    )

    val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val outputFormat = SimpleDateFormat("HH:mm - dd/MM", Locale.getDefault())

    return try {
        val date = inputFormat.parse(this)
        val formattedDateTemp = outputFormat.format(date!!)

        val month = mapOfMonths[formattedDateTemp.takeLast(2).toInt()]
        val formattedDate = formattedDateTemp.dropLast(2) + month

        formattedDate
    } catch (e: ParseException) {
        "${e.printStackTrace()}"
    }

}
