package com.bytcore.cmndroid

fun Boolean?.orFalse(): Boolean {
    return this ?: false
}

fun Int?.orZero(): Int {
    return this ?: 0
}

fun Long?.orZero(): Long {
    return this ?: 0
}

fun Float?.orZero(): Float {
    return this ?: 0F
}

fun Double?.orZero(): Double {
    return this ?: 0.0
}
