package com.bytcore.cmndroid.utils

object NumberUtils {

    fun getTwoDecimalText(value: Double? = null): String? {
        value ?: return null
        return String.format("%.2f", value)
    }
}