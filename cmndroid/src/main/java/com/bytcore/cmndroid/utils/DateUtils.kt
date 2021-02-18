package com.bytcore.cmndroid.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    private const val UTC = "UTC"
    private const val ISO_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"

    private val utcDateFormatter by lazy {
        SimpleDateFormat(ISO_DATE_FORMAT, Locale.ENGLISH).apply {
            timeZone = TimeZone.getTimeZone(UTC)
        }
    }

    fun convertTimestampToISO(timestamp: Long): String {
        val dateFormatter = SimpleDateFormat(ISO_DATE_FORMAT, Locale.getDefault())
        return dateFormatter.format(Date(timestamp))
    }

    fun convertTimestampToUtcTime(timestamp: Long): String {
        return utcDateFormatter.format(Date(timestamp))
    }
}
