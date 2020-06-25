package com.pengdst.amikomcare.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    fun getDate(date: Date? = Calendar.getInstance().time,
                pattern: String? = "yyyy-mm-dd",
                locale: Locale? = Locale.getDefault()): String {

        val dateFormat = SimpleDateFormat(pattern, locale)
        return dateFormat.format(date)

    }

    fun getDate(): String {
        val date: Date? = Calendar.getInstance().time

        val dateFormat = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault())
        return dateFormat.format(date)
    }

}